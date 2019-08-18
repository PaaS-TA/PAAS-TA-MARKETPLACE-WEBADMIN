package org.openpaas.paasta.marketplace.web.admin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.web.admin.util.SecurityUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class AuthUserTokenHeaderInterceptor implements ClientHttpRequestInterceptor {

    private String authTokenHeaderName;
    private String cfAuthBearerTokenHeaderName;

    public AuthUserTokenHeaderInterceptor(String authTokenHeaderName, String cfAuthBearerTokenHeaderName) {
        log.info("AuthUserTokenHeaderInterceptor: init");

        this.authTokenHeaderName = authTokenHeaderName;
        this.cfAuthBearerTokenHeaderName = cfAuthBearerTokenHeaderName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        OAuth2User user = SecurityUtils.getUser();

        log.info("user: {}", user);

        if (user != null) {
            request.getHeaders().set(authTokenHeaderName, httpServletRequest.getSession().getAttribute("yourName").toString());
            request.getHeaders().set(cfAuthBearerTokenHeaderName, httpServletRequest.getSession().getAttribute("token").toString());
        }

        return execution.execute(request, body);
    }

}
