package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.web.admin.service.AdminCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    @Autowired
    public AdminCategoryService adminCategoryService;

    /**
     * 카테고리 메인 페이지로 이동 및 목록 조회
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping
    public String getCategoryListMain(Model model) {
        model.addAttribute("directionUp", Category.Direction.Up);
        model.addAttribute("directionDown", Category.Direction.Down);
        model.addAttribute("categories", adminCategoryService.getCategoryList());
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
        return adminCategoryService.createCategory(category);
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
        model.addAttribute("category", adminCategoryService.getCategory(id));
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
        model.addAttribute("category", adminCategoryService.getCategory(id));
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
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        return adminCategoryService.updateCategory(id, category);
    }

    /**
     * 카테고리 삭제
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public List<Category> deleteCategory(@PathVariable Long id){
        adminCategoryService.deleteCategory(id);

        return adminCategoryService.getCategoryList();
    }


    /**
     * 카테고리 방향 이동
     *
     * @param id the id
     * @param direction the direction
     * @return Category
     */
    @PutMapping("/{id}/{direction}")
    @ResponseBody
    public Category updateSeq(@PathVariable Long id, @PathVariable Category.Direction direction){
        return adminCategoryService.updateSeq(id, direction);
    }

}
