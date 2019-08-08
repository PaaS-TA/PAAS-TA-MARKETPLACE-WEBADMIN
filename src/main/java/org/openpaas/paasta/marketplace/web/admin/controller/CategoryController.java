package org.openpaas.paasta.marketplace.web.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.web.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    /**
     * 카테고리 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping
    public String getCategoryListMain(Model model) {
        model.addAttribute("categories", categoryService.getCategoryList());
        return "contents/category-list";
    }

//    @GetMapping
//    @ResponseBody
//    public List<Category> getCategoryList() {
//        return categoryService.getCategoryList();
//    }


    /**
     * 카테고리 등록 페이지로 이동한다.
     *
     * @return
     */
    @GetMapping("/create")
    public String createCategoryMain(){
        return "contents/category-create";
    }


    /**
     * 카테고리 상세 조회한다.
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public String getCategoryMain(Model model, @PathVariable("id") Long id){
        model.addAttribute("category", categoryService.getCategory(id));
        return "contents/category-detail";
    }

}
