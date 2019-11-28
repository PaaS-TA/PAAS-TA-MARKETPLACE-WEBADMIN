package org.openpaas.paasta.marketplace.web.admin.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	private final String ERROR_RESPONSE_HEADER_KEY = "X-ERROR-RESPONSE-FORMAT";
	private final String ERROR_RESPONSE_FORMAT_HTML = "HTML";
	private final String ERROR_RESPONSE_FORMAT_JSON = "JSON";

	
	@ExceptionHandler(Exception.class)
	public String handleError(HttpServletRequest request, HttpServletResponse response, Exception exception, Model model) {
		String errorResponseFormat = "HTML";
		
		// Error Response Format 판별
		String xErrorResponseFormat = request.getHeader(ERROR_RESPONSE_HEADER_KEY);
		if (ERROR_RESPONSE_FORMAT_JSON.equalsIgnoreCase(xErrorResponseFormat)) {
			errorResponseFormat = ERROR_RESPONSE_FORMAT_JSON;
		}

    	String errorUrl = "error/error";
    	String errorCode = "";
    	String errorMessage = "";
    	String exceptionMsg = "";
    	String exceptionTrace = "";
    	int errorStatusCode = 0;

    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	if (status != null) {
    		errorCode = status.toString();
    		HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(errorCode));
    		errorMessage = httpStatus.getReasonPhrase();
    	}
    	
    	if (exception != null) {
    		exceptionMsg = exception.getMessage();
    		
    		try {
    			StringWriter sw = new StringWriter();
    			exception.printStackTrace(new PrintWriter(sw));
    			exceptionTrace = sw.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	errorStatusCode = getErrorStatusCode(errorCode, exception);
    	response.setStatus(errorStatusCode);

    	// Response 생성
    	if (ERROR_RESPONSE_FORMAT_JSON.equalsIgnoreCase(errorResponseFormat)) {
    		errorUrl = "error/error-json";
    		exceptionTrace = exceptionTrace.replaceAll("\"", "”").replaceAll("\'", "’");
    		
    		model.addAttribute("resultCode", "FAIL");
    		model.addAttribute("timestamp", new Date());
    		model.addAttribute("requestURI", request.getRequestURI());
    		model.addAttribute("errorCode", errorStatusCode);
    		model.addAttribute("errorMessage", errorMessage);
    		model.addAttribute("exceptionMessage", exceptionMsg);
    		model.addAttribute("exceptionTrace", exceptionTrace);
    		
    	} else {
	    	StringBuffer errorInfo = new StringBuffer();
	    	errorInfo.append("\n [ExceptionHandlingController] Error Infomation");
	    	errorInfo.append("\n Result Code: FAIL");
	    	errorInfo.append(String.format("\n Timestamp: %s", new Date()));
	    	errorInfo.append(String.format("\n RequestURI: %s", request.getRequestURI()));
	    	errorInfo.append(String.format("\n Error Code: %s", errorStatusCode));
	    	errorInfo.append(String.format("\n Error Message: %s", errorMessage));
	    	errorInfo.append(String.format("\n Exception Message: %s", exceptionMsg));
	    	errorInfo.append(String.format("\n Exception Trace: \n %s", exceptionTrace));
	    	model.addAttribute("errorInfo", errorInfo.toString());
    	}
    	
		return errorUrl;
	}
	
	private int getErrorStatusCode(String code, Exception exception) {
		int errorStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		
		if (!StringUtils.isEmpty(code)) {
			errorStatusCode = Integer.parseInt(code);
			return errorStatusCode;
		}
		
		// 빈값인지 검사
		String msgTemp = exception.getMessage();
		if (StringUtils.isEmpty(msgTemp)) {
			return errorStatusCode;
		}
		
		// 숫자만 추출하여 빈값인지 검사
		msgTemp = msgTemp.replaceAll("[^0-9]", "");
		if (StringUtils.isEmpty(msgTemp)) {
			return errorStatusCode;
		}

		// 추출한 값이 HttpStatus 범위에 존재하는지 검사
		int tempCode = Integer.parseInt(msgTemp);
		if (tempCode >= 200 && 600 >= tempCode) {
			errorStatusCode = tempCode;
		}
		
		return errorStatusCode;
	}
}
