package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoftwareService {

    private final int PAGING_SIZE = 10;
    private final String SORT = "id,asc";


    @SneakyThrows
    public List<Category> getCategories() {
        return paasApiRest.getForObject("/categories", List.class);
    }


    private final RestTemplate paasApiRest;
    public CustomPage<Software> getSoftwareList(String queryParamString) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/my/page" + queryParamString)
                .queryParam("size", PAGING_SIZE)
                .queryParam("sort", SORT)
                .build().encode()
                .toString();

        ResponseEntity<CustomPage<Software>> responseEntity = paasApiRest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Software>>() {});
        CustomPage<Software> customPage = responseEntity.getBody();
        Page<Software> page = customPage.toPage();
        return customPage;
    }

    /*
    public Software getSoftware(Long id) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/{id}")
                .build()
                .expand(id)
                .toString();

        return paasApiRest.getForObject(url, Software.class);
    }
     */
}
