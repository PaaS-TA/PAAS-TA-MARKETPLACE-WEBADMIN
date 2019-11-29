package org.openpaas.paasta.marketplace.web.admin.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Spring Security를 정의한 Class
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	String logoutSuccessUrl = String.format("%s?redirect=%s&client_id=%s"
												, env.getProperty("marketplace.uaa-logout-url")
												, env.getProperty("marketplace.uaa-logout-rediredct-url")
												, env.getProperty("marketplace.client-id"));
        turnOffSslChecking();
        http.csrf().disable()
        	.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login/**", "/error/**", "/static/**").permitAll()
                //.antMatchers("/**").hasRole("USER").anyRequest().permitAll()
                //.antMatchers("/admin/**").hasRole("ADMIN")
            	.anyRequest()
            	.authenticated()
            .and()
            .oauth2Login()
            	.clientRegistrationRepository(clientRegistrationRepository())
            	.authorizedClientService(authorizedClientService())
            	.defaultSuccessUrl("/index", true).permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
            	.logoutSuccessUrl(logoutSuccessUrl)
            	.invalidateHttpSession(true)
    			.deleteCookies("SESSION")
    			.permitAll();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.cfClientRegistration());
    }

    private ClientRegistration cfClientRegistration() {
        return ClientRegistration.withRegistrationId(env.getProperty("marketplace.registration"))
                .clientId(env.getProperty("marketplace.client-id"))
                .clientSecret(env.getProperty("marketplace.client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid", "cloud_controller_service_permissions.read", "cloud_controller.read", "cloud_controller.write")
                .redirectUriTemplate(env.getProperty("marketplace.redirect-uri"))
                .authorizationUri(env.getProperty("marketplace.authorization-uri"))
                .tokenUri(env.getProperty("marketplace.token-uri"))
                .userInfoUri(env.getProperty("marketplace.user-info-uri"))
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(env.getProperty("marketplace.jwk-set-uri"))
                .clientName(env.getProperty("marketplace.registration"))
                .build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    // SSL Skip을 위한 함수 configure() 의 34번째 라인에서 호출한다.
    private static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init( null, UNQUESTIONING_TRUST_MANAGER, null );
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    private static final TrustManager[] UNQUESTIONING_TRUST_MANAGER = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers(){
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType ){}
                public void checkServerTrusted(X509Certificate[] certs, String authType ){}
            }
    };

}
