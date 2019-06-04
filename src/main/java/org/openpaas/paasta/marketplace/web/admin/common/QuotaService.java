package org.openpaas.paasta.marketplace.web.admin.common;


import org.openpaas.paasta.marketplace.web.admin.model.Quota;
import org.openpaas.paasta.marketplace.web.admin.model.QuotaList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Quota Service
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-03-29
 */
@Service
public class QuotaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgService.class);

    @Autowired
    RestTemplateService marketApiRest;

    /**
     * Org Quota 생성
     *
     * @param quota the quota
     * @return Quota
     */
    public Quota createOrgQuota(Quota quota, String token){
        return marketApiRest.send(AdminConstants.TARGET_API_CF,"/v3/orgs/quota-definitions", token, HttpMethod.POST, quota, Quota.class);
    }

    /**
     * Org Quota 목록 조회
     *
     * @return QuotaList
     */
    public QuotaList getOrgQuotas(String token) {
        return marketApiRest.send(AdminConstants.TARGET_API_CF,"/v3/orgs/quota-definitions", token, HttpMethod.GET, null, QuotaList.class);
    }
}
