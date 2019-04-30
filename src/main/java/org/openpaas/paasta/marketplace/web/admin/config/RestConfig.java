package org.openpaas.paasta.marketplace.web.admin.config;

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

    @Value("${cf.java.client.api.uri}")
    public String cfJavaClientApiUri;

    @Value("${cf.java.client.api.authorization.username}")
    public String cfJavaClientApiUsername;

    @Value("${cf.java.client.api.authorization.password}")
    public String cfJavaClientApiPassword;

    @Value("${market.place.api.uri}")
    public String marketApiUri;

    @Value("${market.place.api.authorization.username}")
    public String marketApiUsername;

    @Value("${market.place.api.authorization.password}")
    public String marketApiPassword;

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    //private static final String CF_AUTHORIZATION_HEADER_KEY = "cf-Authorization";
    private String uaaAccessToken;


    @Bean
    RestTemplate cfJavaClientApiRest() {
        RestTemplate rest = new RestTemplate();
        rest.setUriTemplateHandler(new DefaultUriBuilderFactory(cfJavaClientApiUri));
        rest.getInterceptors().add(cfJavaClientApiInterceptor());
        rest.getInterceptors().add(loggingInterceptor());

        return rest;
    }

    @Bean
    RestTemplate marketApiRest() {
        RestTemplate rest = new RestTemplate();
        rest.setUriTemplateHandler(new DefaultUriBuilderFactory(marketApiUri));
        rest.getInterceptors().add(marketApiInterceptor());
        rest.getInterceptors().add(loggingInterceptor());

        return rest;
    }


    @Bean
    AuthHeaderInterceptor marketApiInterceptor() {
        AuthHeaderInterceptor tokenHeaderInterceptor = new AuthHeaderInterceptor(AUTHORIZATION_HEADER_KEY, marketApiUsername, marketApiPassword);

        return tokenHeaderInterceptor;
    }

    @Bean
    AuthHeaderInterceptor cfJavaClientApiInterceptor() {
        AuthHeaderInterceptor tokenHeaderInterceptor = new AuthHeaderInterceptor(AUTHORIZATION_HEADER_KEY, cfJavaClientApiUsername, cfJavaClientApiPassword);

        return tokenHeaderInterceptor;
    }


    @Bean
    UaaAccessTokenInterceptor uaaAccessTokenInterceptor(){
        UaaAccessTokenInterceptor uaaTokenHeaderInterceptor = new UaaAccessTokenInterceptor(uaaAccessToken);

        return uaaTokenHeaderInterceptor;
    }

    @Bean
    LoggingInterceptor loggingInterceptor() {
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        return loggingInterceptor;
    }
}
