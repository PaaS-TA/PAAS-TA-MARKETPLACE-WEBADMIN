package org.openpaas.paasta.marketplace.web.admin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class PropertyService {

	// market
	@Value("${marketplace.api.url}")
    private String marketApiUri;
	
//    @Value("${market.place.api.authorization.username}")
//    private String marketApiUsername;
//
//    @Value("${market.place.api.authorization.password}")
//    private String marketApiPassword;

}
