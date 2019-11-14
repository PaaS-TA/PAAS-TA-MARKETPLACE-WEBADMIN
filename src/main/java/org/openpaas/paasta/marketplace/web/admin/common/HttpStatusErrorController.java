package org.openpaas.paasta.marketplace.web.admin.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@Slf4j
@Controller
public class HttpStatusErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Exception exception, Model model) {
    	String errorUrl = "error/error";
    	String code = "";
    	String msg = "";
    	String exceptionMsg = "";
    	String exceptionTrace = "";

    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	if (status != null) {
    		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
    		code = status.toString();
    		msg = httpStatus.getReasonPhrase();
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
    	
    	StringBuffer errorInfo = new StringBuffer();
    	errorInfo.append("\n [HttpStatusErrorController] Error Infomation");
    	errorInfo.append(String.format("\n Timestamp: %s", new Date()));
    	errorInfo.append(String.format("\n RequestURI: %s", request.getRequestURI()));
    	errorInfo.append(String.format("\n Error Code: %s", code));
    	errorInfo.append(String.format("\n Error Message: %s", msg));
    	errorInfo.append(String.format("\n Exception Message: %s", exceptionMsg));
    	errorInfo.append(String.format("\n Exception Trace: \n %s", exceptionTrace));
    	model.addAttribute("errorInfo", errorInfo.toString());
        
		return errorUrl;
    }
}
