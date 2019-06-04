package org.openpaas.paasta.marketplace.web.admin.category;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * Category Controller
 *
 * @author peter
 * @version 1.0
 * @since 2019-05-29
 */
@Slf4j
@RestController
public class CategoryController {

	@Autowired
    private CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     *
     * @return CategoryList
     */
    @GetMapping(value = AdminConstants.URI_WEB_CATEGORY_LIST)
    public CategoryList getCategoryList(){
    	log.info("category...");
        return categoryService.getCategoryListByDeleteYn();
    }

}
