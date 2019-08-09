package org.openpaas.paasta.marketplace.web.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
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
     * 카테고리 메인페이지로 이동
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
     * 카테고리 등록 페이지로 이동
     *
     * @return String
     */
    @GetMapping(value = "/create")
    public String createCategoryMain(){
        return "contents/category-create";
    }


    /**
     * 카테고리 등록
     *
     * @param category the category
     * @return Category
     */
    @PostMapping
    @ResponseBody
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }


    /**
     * 카테고리 상세 조회
     *
     * @param model the model
     * @param id the id
     * @return String
     */
    @GetMapping(value = "/{id}")
    public String getCategoryMain(Model model, @PathVariable Long id){
        model.addAttribute("category", categoryService.getCategory(id));
        return "contents/category-detail";
    }


    /**
     * 카테고리를 수정 페이지로 이동
     *
     * @param model the model
     * @param id the id
     * @return String
     */
    @GetMapping(value = "/{id}/modify")
    public String modifyCategoryMain(Model model, @PathVariable Long id){
        model.addAttribute("category", categoryService.getCategory(id));
        return "contents/category-modify";
    }


    /**
     * 카테고리 수정
     *
     * @param id the id
     * @param category the category
     * @return Category
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public Category modifyCategory(@PathVariable Long id, @RequestBody Category category){
        return categoryService.modifyCategory(id, category);
    }

}
