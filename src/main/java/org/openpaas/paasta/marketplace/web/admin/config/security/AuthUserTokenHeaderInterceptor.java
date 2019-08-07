package org.openpaas.paasta.marketplace.web.admin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.User;
import org.openpaas.paasta.marketplace.web.admin.util.SecurityUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class AuthUserTokenHeaderInterceptor implements ClientHttpRequestInterceptor {

    private String authTokenHeaderName;

    public AuthUserTokenHeaderInterceptor(String authTokenHeaderName) {
        log.info("AuthUserTokenHeaderInterceptor: init");

        this.authTokenHeaderName = authTokenHeaderName;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        User user = SecurityUtils.getUser();

        log.info("user: {}", user);

        if (user != null) {
            request.getHeaders().set(authTokenHeaderName, user.getToken());
        }

        request.getHeaders().set(authTokenHeaderName, "hrjin");

        return execution.execute(request, body);
    }

}
