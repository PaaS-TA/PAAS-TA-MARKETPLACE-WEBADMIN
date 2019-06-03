package org.openpaas.paasta.marketplace.web.admin.common;

import org.cloudfoundry.client.v2.organizations.ListOrganizationsResponse;
import org.openpaas.paasta.marketplace.web.admin.model.Org;
import org.openpaas.paasta.marketplace.web.admin.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author hrjin
 * @version 1.0
 * @since 2019-03-27
 */
@Service
public class OrgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgService.class);


    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;


    /**
     * 조직명 중복검사를 실행한다.
     *
     * @param orgName the org name
     * @return boolean
     *
     * 권한 : 사용자
     */
    public boolean isExistOrgByOrgName(String orgName) {
        return cfJavaClientApiRest.getForObject("/v3/orgs/" + orgName + "/exist", boolean.class);
    }


    /**
     * Org 생성 V3 (-> 쿼터 지정 부분이 없음.)
     *
     * @return Org
     */
    public Org createOrgV3(String orgName){
        return cfJavaClientApiRest.postForObject("/v3/orgs", orgName, Org.class);
    }


    /**
     * Org 생성 (-> 쿼터 지정 부분이 있음.)
     *
     * @param org the org
     * @return Map
     */
    public Map createOrg(Org org){
        return cfJavaClientApiRest.postForObject("/v3/orgs", org, Map.class);
    }


    public void associateOrgUserRoles(String orgId, UserRole.RequestBody body) {
        cfJavaClientApiRest.put("/v3/orgs/" + orgId + "/user-roles", body);
    }

    /**
     * 관리자 권한으로 Org 목록 조회
     *
     * @return ListOrganizationsResponse
     */
    public ListOrganizationsResponse getOrgsListV3() {
        return cfJavaClientApiRest.getForObject("/v3/orgs-admin", ListOrganizationsResponse.class);
    }
}
