package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminSoftwareService {

    @Autowired
    private final RestTemplate paasApiRest;

    @SneakyThrows
    public Software createSoftware(Software software) {
        return paasApiRest.postForObject("/softwares", software, Software.class);
    }

    @SneakyThrows
    public List<Category> getAdminCategories() {
        return paasApiRest.getForObject("/admin/categories", List.class);
    }

    public CustomPage<Software> getAdminSoftwareList(String queryParamString) {
        ResponseEntity<CustomPage<Software>> responseEntity = paasApiRest.exchange("/admin/softwares/page" + queryParamString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Software>>() {});
        CustomPage<Software> customPage = responseEntity.getBody();
        return customPage;
    }

    public Software getAdminSoftwares(Long id) {
        String url = UriComponentsBuilder.newInstance().path("/admin/softwares/{id}")
                .build()
                .expand(id)
                .toString();

        return paasApiRest.getForObject(url, Software.class);
    }

    public Software updateAdminSoftware(Long id, Software software) {
        String url = UriComponentsBuilder.newInstance().path("/admin/softwares/{id}")
                .build()
                .expand(id)
                .toString();

        log.info("updateAdminSoftwared url :: " + url + " Software " + software.toString());
        paasApiRest.put(url, software);
        return getAdminSoftwares(id);
    }

    public SoftwarePlan getSoftwarePlan(Long id) {
        log.info("getSoftwarePlan :: " + id );
        String url = UriComponentsBuilder.newInstance().path("/admin/softwares/plan/{id}")
                .build()
                .expand(id)
                .toString();

        return paasApiRest.getForObject(url, SoftwarePlan.class);
    }

    public List<SoftwarePlan> getSoftwarePlanList(Long id) {
        String url = UriComponentsBuilder.newInstance().path("/admin/softwares/plan/{id}/list?sort=name,asc")
                .build()
                .expand(id)
                .toString();
        return paasApiRest.getForObject(url, List.class);
    }


    public List<SoftwarePlan> getPlanHistoryList(Long id, String queryParamString) {
        return paasApiRest.getForObject("/admin/softwares/plan/" + id + "/histories" + queryParamString, List.class);
    }

    public List<SoftwarePlan> getApplyMonth(Long id, String applyMonth) {
        return paasApiRest.getForObject("/admin/softwares/plan/" + id + "/applyMonth?applyMonth=" + applyMonth, List.class);
    }

    public List<SoftwareHistory> getHistoryList(Long id, String queryParamString) {
        return paasApiRest.getForObject("/admin/softwares/" + id + "/histories" + queryParamString, List.class);
    }

    public TestSoftwareInfo deployTestSoftware(Long id, Long planId, TestSoftwareInfo testSoftwareInfo) {
        return paasApiRest.postForObject("/admin/softwares/" + id + "/plan/" + planId, testSoftwareInfo, TestSoftwareInfo.class);
    }

    public List<TestSoftwareInfo> getAdminTestSwInfoList(Long id) {
        return paasApiRest.getForObject("/admin/softwares/" + id + "/testSwInfo", List.class);
    }

    public Map getRecentAppLog(String appGuid) {
        return paasApiRest.getForObject("/admin/apps/" + appGuid + "/recentLogs", Map.class);
    }
}
