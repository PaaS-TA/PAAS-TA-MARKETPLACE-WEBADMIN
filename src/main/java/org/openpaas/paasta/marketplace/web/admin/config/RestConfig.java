package org.openpaas.paasta.marketplace.web.admin.config;

import org.openpaas.paasta.marketplace.web.admin.config.security.AuthUserTokenHeaderInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * RestTemplate Configuration
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-04-08
 */
@Configuration
public class RestConfig {
    public static final String AUTH_TOKEN_HEADER_NAME = "Authorization";
    private static final String CF_BEARER_TOKEN_NAME = "cf-Authorization";

    @Value("${marketplace.api.url}")
    private String marketplaceApi;

    @Bean
    RestTemplate paasApiRest() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(marketplaceApi));
        restTemplate.getInterceptors().add(paasUserInterceptor());
        return restTemplate;
    }

    @Bean
    AuthUserTokenHeaderInterceptor paasUserInterceptor() {
        AuthUserTokenHeaderInterceptor tokenHeaderInterceptor = new AuthUserTokenHeaderInterceptor(AUTH_TOKEN_HEADER_NAME, CF_BEARER_TOKEN_NAME);
        return tokenHeaderInterceptor;
    }

}
