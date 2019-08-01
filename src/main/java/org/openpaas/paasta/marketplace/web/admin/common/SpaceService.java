//package org.openpaas.paasta.marketplace.web.admin.common;
//
//import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
//import org.openpaas.paasta.marketplace.web.admin.model.Space;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
///**
// * Space Service
// *
// * @author hrjin
// * @version 1.0
// * @since 2019-04-03
// */
//@Service
//public class SpaceService {
//
//    @Autowired
//    RestTemplateService cfApiRest;
//
//
//    /**
//     * Space 생성
//     *
//     * @param space the space
//     * @return Map
//     */
//    public Map createSpace(Space space, String token) {
//        return cfApiRest.send(AdminConstants.TARGET_API_CF,"/v3/spaces", token, HttpMethod.POST, space, Map.class);
//    }
//
//
//    /**
//     * 해당 Org 에 속한 Space 목록 조회 V3
//     *
//     * @param orgGuid the org guid
//     * @return ListSpacesResponse
//     */
//    public ListSpacesResponse getSpacesList(String orgGuid, String token) {
//        return cfApiRest.send(AdminConstants.TARGET_API_CF,"/v3/spaces-admin/" + orgGuid, token, HttpMethod.GET, null, ListSpacesResponse.class);
//    }
//}
