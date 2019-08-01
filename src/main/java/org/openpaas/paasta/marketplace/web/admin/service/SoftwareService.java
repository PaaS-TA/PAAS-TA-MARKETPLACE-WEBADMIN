package org.openpaas.paasta.marketplace.web.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoftwareService {

    private final RestTemplate paasApiRest;

    @SneakyThrows
    public void createSoftware(Software software) {
        Object response = paasApiRest.postForObject("http://localhost:8000/softwares", software, Void.class);
    }

    @SneakyThrows
    public List<Category> getCategories() {
        return paasApiRest.getForObject("http://localhost:8000/categories", List.class);
    }

    public Page<Software> getSoftwares(SoftwareSpecification spec) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/page")
                .queryParam("categoryId", spec.getCategoryId())
                .queryParam("nameLike", spec.getNameLike())
                .build().encode()
                .toString();
        return null;
    }


    public Software getSoftware(Long id) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/{id}")
                .build()
                .expand(id)
                .toString();

        return paasApiRest.getForObject(url, Software.class);
    }

    public void updateSoftware(Software software) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/{id}")
                .build()
                .expand(software.getId())
                .toString();

        paasApiRest.put(url, software);
    }
}
