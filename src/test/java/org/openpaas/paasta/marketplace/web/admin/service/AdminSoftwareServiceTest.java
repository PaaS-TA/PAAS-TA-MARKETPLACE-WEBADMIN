package org.openpaas.paasta.marketplace.web.admin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareHistory;
import org.openpaas.paasta.marketplace.api.domain.SoftwarePlan;
import org.openpaas.paasta.marketplace.api.domain.TestSoftwareInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AdminSoftwareServiceTest extends AbstractMockTest {

    AdminSoftwareService adminSoftwareService;

    @Mock
    ResponseEntity<CustomPage<Software>> softwarePageResponse;

    @Mock
    ResponseEntity<Map> mapResponse;

    @Mock
    CustomPage<Software> softwareCustomPage;

    @Mock
    Page<Software> softwarePage;
    
    @Mock
    ResponseEntity<Long> longResponse;

    boolean deleteTestSoftwareInfoNull;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        adminSoftwareService = new AdminSoftwareService(paasApiRest);

        deleteTestSoftwareInfoNull = false;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createSoftware() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);

        when(paasApiRest.postForObject(eq("/softwares"), any(Software.class), eq(Software.class)))
                .thenReturn(software1);
        Software result = adminSoftwareService.createSoftware(software1);
        assertEquals(software1, result);
    }

    @Test(expected = RuntimeException.class)
    public void createSoftwareError() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);

        when(paasApiRest.postForObject(eq("/softwares"), any(Software.class), eq(Software.class)))
                .thenThrow(new IllegalStateException());
        adminSoftwareService.createSoftware(software1);
    }

    @Test
    public void getAdminCategories() {
        List<Category> categoryList = new ArrayList<>();
        Category category1 = category(1L, "category-01");
        categoryList.add(category1);
        Category category2 = category(2L, "category-02");
        categoryList.add(category2);

        when(paasApiRest.getForObject(startsWith("/admin/categories"), eq(List.class))).thenReturn(categoryList);

        List<Category> result = adminSoftwareService.getAdminCategories();
        assertEquals(categoryList, result);
    }

    @Test(expected = RuntimeException.class)
    public void getAdminCategoriesError() {
        when(paasApiRest.getForObject(startsWith("/admin/categories"), eq(List.class)))
                .thenThrow(new IllegalStateException());

        adminSoftwareService.getAdminCategories();
    }

    @Test
    public void getAdminSoftwareList() {
        Category category = category(1L, "category-01");
        List<Software> softwareList = new ArrayList<>();
        Software software1 = software(1L, "software-01", category);
        softwareList.add(software1);
        Software software2 = software(2L, "software-02", category);
        softwareList.add(software2);

        when(paasApiRest.exchange(startsWith("/admin/softwares/page"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(softwarePageResponse);
        when(softwarePageResponse.getBody()).thenReturn(softwareCustomPage);
        when(softwareCustomPage.getContent()).thenReturn(softwareList);

        CustomPage<Software> result = adminSoftwareService.getAdminSoftwareList("?nameLike=software");
        assertEquals(softwareList, result.getContent());
    }

    @Test
    public void getAdminSoftwares() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);

        when(paasApiRest.getForObject(eq("/admin/softwares/1"), eq(Software.class))).thenReturn(software1);

        Software result = adminSoftwareService.getAdminSoftwares(1L);
        assertEquals(software1, result);
    }

    @Test
    public void updateAdminSoftware() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);

        doNothing().when(paasApiRest).put(eq("/admin/softwares/1"), any(Software.class));

        when(paasApiRest.getForObject(eq("/admin/softwares/1"), eq(Software.class))).thenReturn(software1);

        Software result = adminSoftwareService.updateAdminSoftware(1L, software1);
        assertEquals(software1, result);
    }

    @Test
    public void getSoftwarePlan() {
        Category category = category(1L, "category-01");
        software(1L, "software-01", category);
        SoftwarePlan softwarePlan1 = softwarePlan(1L, 1L);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/plan/1"), eq(SoftwarePlan.class)))
                .thenReturn(softwarePlan1);

        SoftwarePlan result = adminSoftwareService.getSoftwarePlan(1L);
        assertEquals(softwarePlan1, result);
    }

    @Test
    public void getSoftwarePlanList() {
        Category category = category(1L, "category-01");
        software(1L, "software-01", category);
        List<SoftwarePlan> softwarePlanList = new ArrayList<>();
        SoftwarePlan softwarePlan1 = softwarePlan(1L, 1L);
        softwarePlanList.add(softwarePlan1);
        SoftwarePlan softwarePlan2 = softwarePlan(2L, 1L);
        softwarePlanList.add(softwarePlan2);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/plan/1/list"), eq(List.class)))
                .thenReturn(softwarePlanList);

        List<SoftwarePlan> result = adminSoftwareService.getSoftwarePlanList(1L);
        assertEquals(softwarePlanList, result);
    }

    @Test
    public void getPlanHistoryList() {
        Category category = category(1L, "category-01");
        software(1L, "software-01", category);
        List<SoftwarePlan> softwarePlanList = new ArrayList<>();
        SoftwarePlan softwarePlan1 = softwarePlan(1L, 1L);
        softwarePlanList.add(softwarePlan1);
        SoftwarePlan softwarePlan2 = softwarePlan(2L, 1L);
        softwarePlanList.add(softwarePlan2);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/plan/1/histories"), eq(List.class)))
                .thenReturn(softwarePlanList);

        List<SoftwarePlan> result = adminSoftwareService.getPlanHistoryList(1L, "?sort=id,asc");
        assertEquals(softwarePlanList, result);
    }

    @Test
    public void getApplyMonth() {
        Category category = category(1L, "category-01");
        software(1L, "software-01", category);
        List<SoftwarePlan> softwarePlanList = new ArrayList<>();
        SoftwarePlan softwarePlan1 = softwarePlan(1L, 1L);
        softwarePlanList.add(softwarePlan1);
        SoftwarePlan softwarePlan2 = softwarePlan(2L, 1L);
        softwarePlanList.add(softwarePlan2);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/plan/1/applyMonth"), eq(List.class)))
                .thenReturn(softwarePlanList);

        List<SoftwarePlan> result = adminSoftwareService.getApplyMonth(1L, "201911");
        assertEquals(softwarePlanList, result);
    }

    @Test
    public void getHistoryList() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);
        List<SoftwareHistory> softwareHistoryList = new ArrayList<>();
        SoftwareHistory softwareHistory1 = softwareHistory(1L, software1);
        softwareHistoryList.add(softwareHistory1);
        SoftwareHistory softwareHistory2 = softwareHistory(2L, software1);
        softwareHistoryList.add(softwareHistory2);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/1/histories"), eq(List.class)))
                .thenReturn(softwareHistoryList);

        List<SoftwareHistory> result = adminSoftwareService.getHistoryList(1L, "?sort=id,asc");
        assertEquals(softwareHistoryList, result);
    }

    @Test
    public void deployTestSoftware() {
        TestSoftwareInfo testSoftwareInfo1 = testSoftwareInfo(1L, 7L, 13L);

        when(paasApiRest.postForObject(eq("/admin/softwares/7/plan/13"), any(TestSoftwareInfo.class),
                eq(TestSoftwareInfo.class))).thenReturn(testSoftwareInfo1);
        TestSoftwareInfo result = adminSoftwareService.deployTestSoftware(7L, 13L, testSoftwareInfo1);
        assertEquals(testSoftwareInfo1, result);
    }

    @Test
    public void getAdminTestSwInfoList() {
        List<TestSoftwareInfo> testSoftwareInfoList = new ArrayList<>();
        TestSoftwareInfo testSoftwareInfo1 = testSoftwareInfo(1L, 7L, 13L);
        testSoftwareInfoList.add(testSoftwareInfo1);
        TestSoftwareInfo testSoftwareInfo2 = testSoftwareInfo(2L, 7L, 27L);
        testSoftwareInfoList.add(testSoftwareInfo2);

        when(paasApiRest.getForObject(startsWith("/admin/softwares/7/testSwInfo"), eq(List.class)))
                .thenReturn(testSoftwareInfoList);

        List<TestSoftwareInfo> result = adminSoftwareService.getAdminTestSwInfoList(7L);
        assertEquals(testSoftwareInfoList, result);
    }

    @Test
    public void getRecentAppLog() {
        Map<String, List<?>> map = new TreeMap<>();

        when(paasApiRest.getForObject(eq("/admin/apps/x/recentLogs"), eq(Map.class))).thenReturn(map);

        Map<?, ?> result = adminSoftwareService.getRecentAppLog("x");
        assertEquals(map, result);
    }

    @Test
    public void deleteDeployTestApp() {
        Map<String, TestSoftwareInfo.Status> map = new TreeMap<>();
        map.put("RESULT", TestSoftwareInfo.Status.Successful);

        when(paasApiRest.exchange(eq("/admin/softwares/7/testSwInfo/1/app/x"), eq(HttpMethod.DELETE), eq(null),
                eq(Map.class))).thenReturn(mapResponse);
        if (!deleteTestSoftwareInfoNull) {
            when(mapResponse.getBody()).thenReturn(map);
        } else {
            when(mapResponse.getBody()).thenReturn(null);
        }

        Map<String, TestSoftwareInfo.Status> result = adminSoftwareService.deleteDeployTestApp(7L, 1L, "x");
        if (!deleteTestSoftwareInfoNull) {
            assertEquals(map, result);
        } else {
            assertNull(result);
        }
    }

    @Test
    public void deleteDeployTestAppDeleteTestSoftwareInfoNull() {
        deleteTestSoftwareInfoNull = true;

        deleteDeployTestApp();
    }
    
    // 배포 테스트 실패한 앱 삭제
    @Test
    public void deleteDeployTestFailedApp() {
    	Long testFailedAppId = 1L;
    	
    	when(paasApiRest.exchange(startsWith("/admin/softwares/testFailed/app/"), eq(HttpMethod.DELETE), eq(null), eq(Long.class)))
    					.thenReturn(longResponse);
    	when(longResponse.getBody()).thenReturn(1L);
    	
    	Map<String,Object> result = adminSoftwareService.deleteDeployTestFailedApp(testFailedAppId);
    	assertEquals("Successful", result.get("RESULT"));
    }
    
    // 배포 테스트 실패한 앱 삭제
    @Test
    public void deleteDeployTestFailedAppWithEmpty() {
    	Long testFailedAppId = 1L;
    	
    	when(paasApiRest.exchange(startsWith("/admin/softwares/testFailed/app/"), eq(HttpMethod.DELETE), eq(null), eq(Long.class))).thenReturn(longResponse);
    	when(longResponse.getBody()).thenReturn(0L);
    	
    	Map<String,Object> result = adminSoftwareService.deleteDeployTestFailedApp(testFailedAppId);
    	assertEquals("Fail", result.get("RESULT"));
    }
    
    // 판매된 소프트웨어의 카운트정보 조회
    @Test
    public void getSoftwareInstanceCountMap() {
    	List<Long> softwareIdList = Arrays.asList(1L, 2L);
    	Map<String,Object> mockMap = new HashMap<String,Object>();
    	mockMap.put("1", 10);
    	mockMap.put("2", 20);
    			
    	when(paasApiRest.getForObject(startsWith("/softwares/instanceCount"), eq(Map.class))).thenReturn(mockMap);
    	
    	Map<String,Object> result = adminSoftwareService.getSoftwareInstanceCountMap(softwareIdList);
    	assertEquals(mockMap.get("1"), result.get("1"));
    }
}
