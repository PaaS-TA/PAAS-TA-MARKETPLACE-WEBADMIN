package org.openpaas.paasta.marketplace.web.admin.profiles;

import javax.servlet.http.HttpServletRequest;

import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.util.DateUtils;
import org.openpaas.paasta.marketplace.web.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


/**
 * 판매자 프로필 Controller
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-05-07
 */
@RestController
@Slf4j
public class SellerProfileController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SellerProfileService sellerProfileService;

    /**
     * 프로필 목록 조회 화면
     * 
     * @param httpServletRequest
     * @param id
     * @return
     */
    @GetMapping(value = AdminConstants.URI_WEB_SELLER_PROFILE_LIST)
    public ModelAndView getProfileListPage(HttpServletRequest httpServletRequest) {
    	// 화면 변수 처리
    	return commonService.setPathVariables(httpServletRequest, AdminConstants.URI_VIEW_PROFILE + "/getlistProfile", new ModelAndView());
    }

    /**
     * 프로필 목록 조회
     *
     * @param id the id
     * @return SellerProfile
     */
    @GetMapping(value = AdminConstants.URI_DB_SELLER_PROFILE_LIST)
    private SellerProfileList getProfileList() {
    	return sellerProfileService.getSellerProfileList();
    }

    /**
     * 프로필 상세 조회
     *
     * @param httpServletRequest the httpServletRequest
     * @return ModelAndView
     */
    @GetMapping(value = AdminConstants.URI_WEB_SELLER_PROFILE_DETAIL)
    public ModelAndView getProfilePage(HttpServletRequest httpServletRequest, @PathVariable(value = "id") Long id) {
    	// 화면 변수 처리
    	return commonService.setPathVariables(httpServletRequest, AdminConstants.URI_VIEW_PROFILE + "/getProfile", new ModelAndView());
    }


    /**
     * 프로필 상세 조회
     *
     * @param id the id
     * @return SellerProfile
     */
    @GetMapping(value = AdminConstants.URI_DB_SELLER_PROFILE_DETAIL)
    private SellerProfile getProfile(@PathVariable Long id) {
    	SellerProfile seller = sellerProfileService.getProfile(id);
		String createdDate = DateUtils.getConvertDate(seller.getCreateDate(), DateUtils.FORMAT_1);
        String updatedDate = DateUtils.getConvertDate(seller.getCreateDate(), DateUtils.FORMAT_1);
		log.info("createdDate: " + createdDate);
        log.info("updatedDate: " + updatedDate);
		seller.setStrCreateDate(createdDate);
		seller.setStrUpdateDate(updatedDate);

		return seller;
    }

    /**
     * 프로필 수정 페이지 이동
     *
     * @param httpServletRequest the httpServletRequest
     * @return ModelAndView
     */
    @GetMapping(value = AdminConstants.URI_WEB_SELLER_PROFILE_UPDATE)
    public ModelAndView getProfileUpdatePage(HttpServletRequest httpServletRequest, @PathVariable Long id){
        return commonService.setPathVariables(httpServletRequest, AdminConstants.URI_VIEW_PROFILE + "/updateProfile", new ModelAndView());
    }

    /**
     * 프로필 수정
     *
     * @param id the id
     * @param sellerProfile the seller profile
     */
    @PutMapping(value = AdminConstants.URI_WEB_SELLER_PROFILE_UPDATE)
    private SellerProfile updateProfile(@PathVariable Long id, @RequestBody SellerProfile sellerProfile){
        String userId = SecurityUtils.getUserId();
        sellerProfile.setUpdateId(userId);

        return sellerProfileService.updateProfile(id, sellerProfile);
    }
}
