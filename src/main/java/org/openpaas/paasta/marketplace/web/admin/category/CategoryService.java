package org.openpaas.paasta.marketplace.web.admin.category;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.RestTemplateService;
import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
    private RestTemplateService marketApiRest;

    /**
     * GroupCode로 단위코드 목록 조회
     *
     * @param groupCode
     * @return CustomCodeList
     */
    public List<Category> getCategoryListByDeleteYn() {
    	CategoryList categoryList = marketApiRest.send(AdminConstants.TARGET_API_MARKET, AdminConstants.URI_API_CATEGORY, HttpMethod.GET, null, CategoryList.class);
    	return categoryList.getItems();
    }

}
