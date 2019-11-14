package org.openpaas.paasta.marketplace.web.admin.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.Category.Direction;

public class AdminCategoryServiceTest extends AbstractMockTest {

    AdminCategoryService adminCategoryService;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        adminCategoryService = new AdminCategoryService(paasApiRest);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        Category category1 = category(1L, "category-01");
        categoryList.add(category1);
        Category category2 = category(2L, "category-02");
        categoryList.add(category2);

        when(paasApiRest.getForObject(startsWith("/admin/categories"), eq(List.class))).thenReturn(categoryList);

        List<Category> result = adminCategoryService.getCategoryList();
        assertEquals(categoryList, result);
    }
    
    @Test
    public void getCategory() {
        Category category1 = category(1L, "category-01");
        
        when(paasApiRest.getForObject(eq("/admin/categories/1"), eq(Category.class))).thenReturn(category1);
        
        Category result = adminCategoryService.getCategory(1L);
        assertEquals(category1, result);
    }
    
    @Test
    public void updateCategory() {
        Category category1 = category(1L, "category-01");
        
        doNothing().when(paasApiRest).put(eq("/admin/categories/1"), any(Category.class));
        
        when(paasApiRest.getForObject(eq("/admin/categories/1"), eq(Category.class))).thenReturn(category1);
        
        Category result = adminCategoryService.updateCategory(1L, category1);
        assertEquals(category1, result);
    }
    
    @Test
    public void createCategory() {
        Category category1 = category(1L, "category-01");
        
        when(paasApiRest.postForObject(eq("/admin/categories"), any(Category.class), eq(Category.class))).thenReturn(category1);
        
        Category result = adminCategoryService.createCategory(category1);
        assertEquals(category1, result);
    }
    
    @Test
    public void deleteCategory() {
        doNothing().when(paasApiRest).delete(eq("/admin/categories/1"));
        
        adminCategoryService.deleteCategory(1L);
    }

    @Test
    public void updateSeq() {
        Category category1 = category(1L, "category-01");
        
        doNothing().when(paasApiRest).put(eq("/admin/categories/1/Down"), eq(null));
        
        when(paasApiRest.getForObject(eq("/admin/categories/1"), eq(Category.class))).thenReturn(category1);
        
        Category result = adminCategoryService.updateSeq(1L, Direction.Down);
        assertEquals(category1, result);
    }
    
}
