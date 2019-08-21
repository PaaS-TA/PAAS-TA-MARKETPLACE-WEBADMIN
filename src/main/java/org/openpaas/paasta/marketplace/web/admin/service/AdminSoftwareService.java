package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
        return paasApiRest.getForObject("/categories", List.class);
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

}
