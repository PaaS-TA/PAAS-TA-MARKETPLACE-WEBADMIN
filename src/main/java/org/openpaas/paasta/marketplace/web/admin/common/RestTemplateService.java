package org.openpaas.paasta.marketplace.web.admin.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Rest Template Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2019.03.25
 */
@Service
@Slf4j
public class RestTemplateService {
	
    private final PropertyService property;
	
    private final RestTemplate restTemplate;
    
	@Autowired
	public RestTemplateService(PropertyService property, RestTemplate restTemplate) {
		this.property = property;
		this.restTemplate = restTemplate;
	}
	
	public <T> T send(String targetApi, String restUrl, String token, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
		Map<String, String> requestMap = setApiUrlAuthorization(targetApi, token);
		String apiFullUrl = requestMap.get("apiUrl") + restUrl;
		String authorization1 = requestMap.get("authorization1");
		String authorization2 = requestMap.get("authorization2");

		HttpHeaders reqHeaders = new HttpHeaders();

		if(AdminConstants.TARGET_API_CF.equals(targetApi)){
			reqHeaders.add(AdminConstants.CF_AUTHORIZATION_HEADER_KEY, authorization1);
			reqHeaders.add(AdminConstants.AUTHORIZATION_HEADER_KEY, authorization2);
		}else{
			reqHeaders.add(AdminConstants.AUTHORIZATION_HEADER_KEY, authorization1);
		}

        reqHeaders.add(AdminConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
        log.info("<T> T send :: Request : {} {} : {}, Content-Type: {}", httpMethod, apiFullUrl, restUrl, reqHeaders.get(AdminConstants.CONTENT_TYPE));

        try {
			ResponseEntity<T> resEntity =  restTemplate.exchange(apiFullUrl, httpMethod, reqEntity, responseType);
			if (resEntity.getBody() != null) {
	            log.info("Response Type: {}", resEntity.getBody().getClass());
	            log.info(resEntity.getBody().toString());
	        } else {
	            log.info("Response Type: {}", "response body is null");
	        }
	
			return resEntity.getBody();
		} catch (Exception e) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("resultCode", "FAIL");
	        resultMap.put("resultMessage", e.getMessage());
	        ObjectMapper mapper = new ObjectMapper();
	
	        log.error("Error resultMap : {}", resultMap);
	
	        return mapper.convertValue(resultMap, responseType);
		}
	}
	
	/**
	 * 전송용 헤더 생성
	 * 
	 * @param targetApi
	 * @return
	 */
	private Map<String, String> setApiUrlAuthorization(String targetApi, String token) {
        Map<String, String> requestMap = new HashMap<String, String>();

        // Cf API
        if (AdminConstants.TARGET_API_CF.equals(targetApi)) {
            requestMap.put("apiUrl", property.getCfJavaClientApiUri());
            requestMap.put("authorization1", "bearer " + token);
			requestMap.put("authorization2", "Basic " + Base64Utils.encodeToString((property.getCfJavaClientApiUsername() + ":" + property.getCfJavaClientApiPassword()).getBytes(StandardCharsets.UTF_8)));
        }

        // Market API
        if (AdminConstants.TARGET_API_MARKET.equals(targetApi)) {
            requestMap.put("apiUrl", property.getMarketApiUri());
            requestMap.put("authorization1", "Basic " + Base64Utils.encodeToString((property.getMarketApiUsername() + ":" + property.getMarketApiPassword()).getBytes(StandardCharsets.UTF_8)));
        }

        return requestMap;
    }

}