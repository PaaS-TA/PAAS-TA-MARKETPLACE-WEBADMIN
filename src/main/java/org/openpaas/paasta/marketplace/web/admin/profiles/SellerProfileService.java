package org.openpaas.paasta.marketplace.web.admin.profiles;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.openpaas.paasta.marketplace.web.admin.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 판매자 프로필 Service
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-05-07
 */
@Service
@Slf4j
public class SellerProfileService {

    @Autowired
    private RestTemplateService marketApiRest;

    /**
     * 판매자 프로필 목록 조회
     *
     * @param 
     * @return SellerProfileList
     */
    public List<SellerProfile> getSellerProfileList() {
        SellerProfileList profiles = marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_SELLER_PROFILE, HttpMethod.GET, null, SellerProfileList.class);
        return profiles.getItems();
    }

    /**
     * 판매자 프로필 상세 조회
     *
     * @param id the id
     * @return SellerProfile
     */
    public SellerProfile getProfile(Long id) {
        return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_SELLER_PROFILE + "/" + id, HttpMethod.GET, null, SellerProfile.class);
    }

    /**
     * 판매자 프로필 등록
     *
     * @param sellerProfile the seller profile
     * @return SellerProfile
     * @throws Exception 
     */
    public SellerProfile createProfile(SellerProfile sellerProfile) {
    	return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_SELLER_PROFILE, HttpMethod.POST, sellerProfile, SellerProfile.class);
    }

    /**
     * 판매자 프로필 수정
     *
     * @param sellerProfile the seller profile
     */
    public SellerProfile updateProfile(Long id, SellerProfile sellerProfile) {
        return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_SELLER_PROFILE + "/" + id, HttpMethod.PUT, sellerProfile, SellerProfile.class);
    }

//    public SellerProfile getProfileByUserId(String userId) {
//        return marketApiRest.getForObject("/profile/user/" + userId, SellerProfile.class);
//    }
}
