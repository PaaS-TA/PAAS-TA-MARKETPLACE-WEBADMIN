package org.openpaas.paasta.marketplace.web.admin.category;

import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.openpaas.paasta.marketplace.web.admin.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
    private RestTemplateService marketApiRest;

    /**
     * 카테고리 목록 조회
     *
     * @return CategoryList
     */
    public CategoryList getCategoryList() {
    	return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_CATEGORY, null, HttpMethod.GET, null, CategoryList.class);
    }

    /**
     * 카테고리 상세 조회
     * 
     * @param id
     * @return
     */
    public Category getCategory(Long id) {
        return marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_CATEGORY + "/" + id, null, HttpMethod.GET, null, Category.class);
    }

}
