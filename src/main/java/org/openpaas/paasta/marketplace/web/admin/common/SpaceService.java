package org.openpaas.paasta.marketplace.web.admin.common;

import java.util.Map;

import javax.annotation.Resource;

import org.cloudfoundry.client.v3.spaces.CreateSpaceResponse;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.openpaas.paasta.marketplace.web.admin.model.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Space Service
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-04-03
 */
@Service
public class SpaceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceService.class);


    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;


    /**
     * Space 생성 V3
     *
     * @param space the space
     * @return Space
     */
    public CreateSpaceResponse createSpaceV3(Space space) {
        return cfJavaClientApiRest.postForObject("/v3/spaces", space, CreateSpaceResponse.class);
    }


    /**
     * Space 생성 V2
     *
     * @param space the space
     * @return Map
     */
    public Map createSpaceV2(Space space) {
        //return marketApiRest.postForObject("/spaces", space, Space.class);
        //return restTemplateService.send("/spaces", getToken(), HttpMethod.POST, space, Map.class);
        return cfJavaClientApiRest.postForObject("/v2/spaces", space, Map.class);
    }


    /**
     * 해당 Org 에 속한 Space 목록 조회 V3
     *
     * @param orgGuid the org guid
     * @return ListSpacesResponse
     */
    public ListSpacesResponse getSpacesListV3(String orgGuid) {
        return cfJavaClientApiRest.getForObject("/v3/spaces-admin/" + orgGuid, ListSpacesResponse.class);
    }
}
