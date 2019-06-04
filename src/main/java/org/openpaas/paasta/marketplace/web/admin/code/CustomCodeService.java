package org.openpaas.paasta.marketplace.web.admin.code;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.RestTemplateService;
import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Custom Code Service
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-05-08
 */
@Service
public class CustomCodeService {

    @Autowired
    private RestTemplateService marketApiRest;

    /**
     * GroupCode로 단위코드 목록 조회
     *
     * @param groupCode
     * @return CustomCodeList
     */
    public CustomCodeList getUnitCodeListByGroupCode(String groupCode) {
    	return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_CUSTOM_CODE + "/" + groupCode, null, HttpMethod.GET, null, CustomCodeList.class);
    }

}
