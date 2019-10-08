package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Instance;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final AdminSoftwareService adminSoftwareService;
    private final RestTemplate paasApiRest;

    //사용자별 구매 상품 수
    public Map<String, Object> countsOfInstsUser() {
        return paasApiRest.getForObject("/admin/stats/users/instances/counts", Map.class);
    }

    //판매자별 총 등록 상품(승인/대기/반려) 수 조회
    public Map<String, Long> getCountsOfTotalSwsProvider() {
        return paasApiRest.getForObject("/admin/stats/providers/total/softwares/counts", Map.class);
    }

    //판매자별 승인 상품 수 조회
    public Map<String, Long> getCountsOfSwsProvider() {
        return paasApiRest.getForObject("/admin/stats/providers/softwares/counts", Map.class);
    }

    //판매자별 총 판매량 수 조회
    public Map<String, Long> getCountsOfInstanceProvider() {
        return paasApiRest.getForObject("/admin/stats/providers/instances/counts", Map.class);
    }

    //판매 상품(사용 + 정지)수
    public Map<Long, Long> getSoldInstanceCount(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/totalSold/counts/ids");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }


    public Map<Long, Object> getUsingPerAllInstanceByProvider(String providerId, List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/instances/usingAllCount/provider/providerId/" + providerId);
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    // 판매자 승인(status = Approval)상품 수
    public Map<Long, Object> getUsingPerInstanceByProvider(String providerId, List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/usingCount/provider/" + providerId);
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    public Map<String, Long> countOfSwsUsingProvider(List<Long> providerId) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/softwares/counts/provider");
        for (Long id : providerId) {
            builder.queryParam("providerId", id);
        }
        String url = builder.buildAndExpand().toUriString();
        return paasApiRest.getForObject(url, Map.class);
    }

    // 상품별 사용자 수 목록 조회
    public Map<Long, Long> getCountsOfInsts(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/counts/ids");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    public Map<String, Long> getCountsOfInstsUser(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/users/instances/counts/ids");
        for (String id : idIn) {
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

    // 등록된 상품 총 개수 조회
    public long getCountOfTotalSw() {
        return paasApiRest.getForObject("/admin/stats/softwares/counts/total/sum", long.class);
    }


    //판매된 상품 총 개수 조회
    public long getCountOfInstsUsing() {
        return paasApiRest.getForObject("/admin/stats/instances/counts/sum", long.class);
    }

    //과거 사용량 추이 조회(days)
    public Map<String, Object> countsOfInstsMonthly(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/sum/months");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();
        return paasApiRest.getForObject(url, Map.class);
    }

    //과거 사용량 추이 조회(months)
    public Map<String, Object> countsOfInstsProviderMonthly() {
        return paasApiRest.getForObject("/admin/stats/instances/sum/months", Map.class);
    }

    //라인 그래프 [판매자별 현황] 과거 사용량 추이 조회
    /*12개월*/
    public Map<String, Object> countsOfInstCountMonthlyProvider(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats//instances/counts/months/ids");
        for (String id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    /*6개월*/
    public Map<String, Object> countsOfInstsStatsMonthly(List<String> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/sum/months/ids");
        for (String id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    //라인 그래프 [상품별 현황] 과거 사용량 추이 조회
    public Map<String, Object> countsOfInstsProviderMonthlyTransition(List<Long> idIn) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/counts/months");
        for (Long id : idIn) {
            builder.queryParam("idIn", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    //라인 그래프 [판매자별 현황] 과거 사용량 추이 조회
    public Map<String, Object> countsOfInstsUserMonthly(List<String> createdBy) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().path("/admin/stats/instances/sum/user/months");
        for (String id : createdBy) {
            builder.queryParam("createdBy", id);
        }
        String url = builder.buildAndExpand().toUriString();

        return paasApiRest.getForObject(url, Map.class);
    }

    //총 사용자 수 조회
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

    //마켓플레이스 전체 사용자 목록 조회
    public CustomPage<Object> getUserList(String queryParamString) {
        ResponseEntity<CustomPage<Object>> responseEntity = paasApiRest.exchange("/admin/users/page" + queryParamString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Object>>() {});
        CustomPage<Object> customPage = responseEntity.getBody();
        return customPage;
    }

    //사용자 상세 조회
    public Object getUser(String id) {
        String url = UriComponentsBuilder.newInstance().path("/admin/users/{id}")
                .build()
                .expand(id)
                .toString();
        return paasApiRest.getForObject(url, Object.class);
    }

    //상품 판매 현황 목록 조회
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


    /**
     * 판매자가 등록한 상품 중 1개 이상 판매된 상품들의 수
     *
     * @param id
     * @return
     */
    public int countOfSoldSw(String id) {
        // 본인이 등록한 승인된 상품 목록
        CustomPage<Software> softwares = adminSoftwareService.getAdminSoftwareList("?createdBy=" + id + "&status=" + Software.Status.Approval);

        // 구매된 상품 전체 목록
        CustomPage<Instance> instances = getInstanceListBySwId("");

        int count = 0;

        for(int i = 0; i < softwares.getContent().size(); i++) {
            for(int j = 0; j < instances.getContent().size(); j++) {
                if(softwares.getContent().get(i).getId().equals(instances.getContent().get(j).getSoftware().getId())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
