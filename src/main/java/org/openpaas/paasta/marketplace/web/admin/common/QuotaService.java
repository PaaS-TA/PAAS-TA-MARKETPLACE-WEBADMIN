package org.openpaas.paasta.marketplace.web.admin.common;


import org.openpaas.paasta.marketplace.web.admin.model.Quota;
import org.openpaas.paasta.marketplace.web.admin.model.QuotaList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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


    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;

    /**
     * Org Quota 생성
     *
     * @param quota the quota
     * @return Quota
     */
    public Quota createOrgQuota(Quota quota){
        return cfJavaClientApiRest.postForObject("/v3/orgs/quota-definitions", quota, Quota.class);
    }

    /**
     * Org Quota 목록 조회
     *
     * @return QuotaList
     */
    public QuotaList getOrgQuotas() {
        return cfJavaClientApiRest.getForObject("/v3/orgs/quota-definitions", QuotaList.class);
    }
}
