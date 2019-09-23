package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Instance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author hrjin
 * @version 1.0
 * @since 2019-08-30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminStatsService {

    private final RestTemplate paasApiRest;

    /**
     * 판매자별 승인 상품 수 조회
     *
     * @return
     */
    public Map<String, Long> getCountsOfSwsProvider() {
        return paasApiRest.getForObject("/admin/stats/providers/softwares/counts", Map.class);
    }

    /**
     * 판매자별 총 판매량 수 조회
     *
     * @return
     */
    public Map<String, Long> getCountsOfInstanceProvider() {
        return paasApiRest.getForObject("/admin/stats/providers/instances/counts", Map.class);
    }


//    public Map countOfSwsUsingProvider(List<String> idIn) {
//        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/softwares/counts/provider");
//        for (String id : idIn) {
//            builder.queryParam("idIn", id);
//        }
//        String url = builder.buildAndExpand().toUriString();
//        return paasApiRest.getForObject(url, Map.class);
//    }

    /**
     * 상품별 사용자 수 목록 조회
     *
     * @param idIn
     * @return
     */
    public Map<Long, Long> getCountsOfInsts(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/counts/ids");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }


    public Map sellerCountsOfInstsProviderMonthly(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/sum/months");
        for (String id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();
        return paasApiRest.getForObject(url, Map.class);
    }


    /**
     * 상품 총 팔린 개수
     *
     * @param idIn
     * @return
     */
    public Map<Long, Long> getSoldInstanceCount(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/totalSold/counts/ids");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    /**
     * 판매된 상품 총 개수 조회
     *
     * @return
     */
    public long getCountOfInstsUsing() {
        return paasApiRest.getForObject("/admin/stats/instances/counts/sum", long.class);
    }

    /**
     * 과거 사용량 추이 조회(days)
     *
     * @return
     */

    public Map<String, Object> countOfInstsDaily() {
        return paasApiRest.getForObject("/admin/stats/instances/sum/days", Map.class);
    }


    /**
     * 과거 사용량 추이 조회(months)
     *
     * @return
     */
    public Map<String, Object> countsOfInstsProviderMonthly() {
        return paasApiRest.getForObject("/admin/stats/instances/sum/months", Map.class);
    }

    /**
     * 라인 그래프 [상품별 현황] 과거 사용량 추이 조회
     *
     * @return
     */
    public Map<String, Object> countsOfInstsProviderMonthlyTransition(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/counts/months");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    /**
     * 라인 그래프 [판매자별 현황] 과거 사용량 추이 조회
     *
     * @return
     */
    public Map<String, Object> countsOfInstsUserMonthly(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/sum/user/months");
        for (String id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }


    /**
     * 총 사용자 수 조회
     *
     * @return
     */
    public long getCountOfUsersUsing() {
        return paasApiRest.getForObject("/admin/stats/users/counts/sum", long.class);
    }

    public Map getUserStatList(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/users");
        for (String id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();
        return paasApiRest.getForObject(url, Map.class);
    }

    /**
     * 마켓플레이스 전체 사용자 목록 조회
     *
     * @param queryParamString
     * @return
     */
    public CustomPage<Object> getUserList(String queryParamString) {
        ResponseEntity<CustomPage<Object>> responseEntity = paasApiRest.exchange("/admin/users/page" + queryParamString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Object>>() {});
        CustomPage<Object> customPage = responseEntity.getBody();
        return customPage;
    }

    /**
     * 사용자 상세 조회
     *
     * @param id
     * @return
     */
    public Object getUser(String id) {
        String url = UriComponentsBuilder.newInstance().path("/admin/users/{id}")
                .build()
                .expand(id)
                .toString();
        return paasApiRest.getForObject(url, Object.class);
    }


    /**
     * 사용자별 구매 상품 수
     *
     * @return
     */
    public Map<String, Object> countsOfInstsUser() {
        return paasApiRest.getForObject("/admin/stats/users/instances/counts", Map.class);
    }


    /**
     * 상품 판매 현황 목록 조회
     *
     * @param queryString
     * @return
     */
    public CustomPage<Instance> getInstanceListBySwId(String queryString) {
        ResponseEntity<CustomPage<Instance>> responseEntity = paasApiRest.exchange("/instances/page" + queryString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Instance>>() {});
        CustomPage<Instance> customPage = responseEntity.getBody();

        System.out.println("getContent ::: " + customPage.getContent());
        System.out.println("getTotalElements ::: " + customPage.getTotalElements());
        return customPage;
    }

    //Page::Instance
    public CustomPage<Instance> getInstanceListBySwInId(String queryParamString) {
        ResponseEntity<CustomPage<Instance>> responseEntity = paasApiRest.exchange("/instances/my/totalPage" + queryParamString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Instance>>() {});
        CustomPage<Instance> customPage = responseEntity.getBody();

        System.out.println("getContent ::: " + customPage.getContent());
        System.out.println("getTotalElements ::: " + customPage.getTotalElements());
        return customPage;
    }


}
