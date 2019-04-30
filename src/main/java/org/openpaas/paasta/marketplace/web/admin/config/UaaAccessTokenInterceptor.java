package org.openpaas.paasta.marketplace.web.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Uaa Access Token 을 header 에 넣어주는 Interceptor
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-04-09
 */
public class UaaAccessTokenInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UaaAccessTokenInterceptor.class);
    private String uaaAccessToken;
    private static final String CF_AUTHORIZATION_HEADER_KEY = "cf-Authorization";

    public UaaAccessTokenInterceptor(String uaaAccessToken) {
        this.uaaAccessToken = uaaAccessToken;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String bearerAuthToken = makeBearerTokenAuthorization(uaaAccessToken);

        logger.info("베어럴 ~~~ " + bearerAuthToken);

        request.getHeaders().set(CF_AUTHORIZATION_HEADER_KEY, bearerAuthToken);

        return execution.execute(request, body);
    }

    private String makeBearerTokenAuthorization(String uaaAccessToken){
        return "bearer " + uaaAccessToken;
    }
}
