package org.openpaas.paasta.marketplace.web.admin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class PropertyService {

	// market
	@Value("${market.place.api.uri}")
    private String marketApiUri;
	
    @Value("${market.place.api.authorization.username}")
    private String marketApiUsername;

    @Value("${market.place.api.authorization.password}")
    private String marketApiPassword;
    

	// PaaS-TA CF
    @Value("${cf.java.client.api.uri}")
    private String cfJavaClientApiUri;

    @Value("${cf.java.client.api.authorization.username}")
    private String cfJavaClientApiUsername;

    @Value("${cf.java.client.api.authorization.password}")
    private String cfJavaClientApiPassword;

    @Value("${cf.api.url}")
    private String cfApiUrl;

    @Value("${cf.uaa.oauth.info.uri}")
    private String cfUaaUserInfo;

    @Value("${cf.uaa.oauth.token.check.uri}")
    private String cfCheckTokenUri;

    @Value("${cf.uaa.oauth.token.access.uri}")
    private String cfAccessUri;

    @Value("${cf.uaa.oauth.logout.uri}")
    private String cfLogoutUri;

    @Value("${cf.uaa.oauth.authorization.uri}")
    private String cfAuthorizationUri;

    @Value("${cf.uaa.oauth.client.id}")
    private String cfClientId;

    @Value("${cf.uaa.oauth.client.secret}")
    private String cfClientSecret;

}
