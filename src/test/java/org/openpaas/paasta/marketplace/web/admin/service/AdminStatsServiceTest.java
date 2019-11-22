package org.openpaas.paasta.marketplace.web.admin.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.assertj.core.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Instance;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unchecked")
public class AdminStatsServiceTest extends AbstractMockTest {

    AdminStatsService adminStatsService;

    @Mock
    AdminSoftwareService adminSoftwareService;

    @Mock
    ResponseEntity<CustomPage<Object>> objectPageResponse;

    @Mock
    ResponseEntity<CustomPage<Instance>> instancePageResponse;

    @Mock
    CustomPage<Object> objectCustomPage;

    @Mock
    CustomPage<Instance> instanceCustomPage;

    @Mock
    CustomPage<Software> softwareCustomPage;

    boolean instanceListEmpty;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        adminStatsService = new AdminStatsService(adminSoftwareService, paasApiRest);

        instanceListEmpty = false;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void countsOfInstsUser() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);

        when(paasApiRest.getForObject(eq("/admin/stats/users/instances/counts"), eq(Map.class))).thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsUser();
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsSumUser() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);

        when(paasApiRest.getForObject(eq("/admin/stats/users/sum/instances/counts"), eq(Map.class))).thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsSumUser();
        assertEquals(map, result);
    }

    @Test
    public void getCountsOfTotalSwsProvider() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);

        when(paasApiRest.getForObject(eq("/admin/stats/providers/total/softwares/counts"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Long> result = adminStatsService.getCountsOfTotalSwsProvider();
        assertEquals(map, result);
    }

    @Test
    public void getCountsOfSwsProvider() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);

        when(paasApiRest.getForObject(eq("/admin/stats/providers/softwares/counts"), eq(Map.class))).thenReturn(map);

        Map<String, Long> result = adminStatsService.getCountsOfSwsProvider();
        assertEquals(map, result);
    }

    @Test
    public void getCountsOfInstanceProvider() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);

        when(paasApiRest.getForObject(eq("/admin/stats/providers/instances/counts"), eq(Map.class))).thenReturn(map);

        Map<String, Long> result = adminStatsService.getCountsOfInstanceProvider();
        assertEquals(map, result);
    }

    @Test
    public void getSoldInstanceCount() {
        Map<Long, Long> map = new TreeMap<>();
        map.put(1L, 7L);
        map.put(2L, 13L);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/totalSold/counts/ids"), eq(Map.class)))
                .thenReturn(map);

        Map<Long, Long> result = adminStatsService.getSoldInstanceCount(ids);
        assertEquals(map, result);
    }

    @Test
    public void getUsingPerInstanceByProvider() {
        Map<Long, Long> map = new TreeMap<>();
        map.put(1L, 7L);
        map.put(2L, 13L);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/usingCount/provider/" + userId),
                eq(Map.class))).thenReturn(map);

        Map<Long, Object> result = adminStatsService.getUsingPerInstanceByProvider(userId, ids);
        assertEquals(map, result);
    }

    @Test
    public void countOfSwsUsingProvider() {
        Map<String, Long> map = new TreeMap<>();
        map.put("1", 7L);
        map.put("2", 13L);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/softwares/counts/provider"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Long> result = adminStatsService.countOfSwsUsingProvider(ids);
        assertEquals(map, result);
    }

    @Test
    public void getCountsOfInsts() {
        Map<Long, Long> map = new TreeMap<>();
        map.put(1L, 7L);
        map.put(2L, 13L);
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/counts/ids"), eq(Map.class))).thenReturn(map);

        Map<Long, Long> result = adminStatsService.getCountsOfInsts(ids);
        assertEquals(map, result);
    }

    @Test
    public void getCountsOfInstsUser() {
        Map<String, Long> map = new TreeMap<>();
        map.put("a", 7L);
        map.put("b", 13L);
        List<String> ids = new ArrayList<>();
        ids.add("a");
        ids.add("b");

        when(paasApiRest.getForObject(startsWith("/admin/stats/users/instances/counts/ids"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Long> result = adminStatsService.getCountsOfInstsUser(ids);
        assertEquals(map, result);
    }

    @Test
    public void getCountOfTotalSw() {
        when(paasApiRest.getForObject(eq("/admin/stats/softwares/counts/total/sum"), eq(long.class))).thenReturn(7L);

        long result = adminStatsService.getCountOfTotalSw();
        assertEquals(7L, result);
    }

    @Test
    public void getCountOfInstsUsing() {
        when(paasApiRest.getForObject(eq("/admin/stats/instances/counts/sum"), eq(long.class))).thenReturn(7L);

        long result = adminStatsService.getCountOfInstsUsing();
        assertEquals(7L, result);
    }

    @Test
    public void countsOfInstsMonthly() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/sum/months"), eq(Map.class))).thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsMonthly(ids);
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsProviderMonthly() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/sum/months"), eq(Map.class))).thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsProviderMonthly();
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsProviderMonthlyTransition() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/counts/months"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsProviderMonthlyTransition(ids);
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsStatsMonthly() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));
        List<String> ids = new ArrayList<>();
        ids.add("x");
        ids.add("y");

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/sum/months/ids"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsStatsMonthly(ids);
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsUsersMonthly() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));
        List<String> ids = new ArrayList<>();
        ids.add("x");
        ids.add("y");

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/sum/users/months"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsUsersMonthly(ids);
        assertEquals(map, result);
    }

    @Test
    public void countsOfInstsSingleUserMonthly() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));

        when(paasApiRest.getForObject(startsWith("/admin/stats/instances/sum/user/months"), eq(Map.class)))
                .thenReturn(map);

        Map<String, Object> result = adminStatsService.countsOfInstsSingleUserMonthly("x");
        assertEquals(map, result);
    }

    @Test
    public void getCountOfUsersUsing() {
        when(paasApiRest.getForObject(eq("/admin/stats/users/counts/sum"), eq(long.class))).thenReturn(7L);

        long result = adminStatsService.getCountOfUsersUsing();
        assertEquals(7L, result);
    }

    @Test
    public void getUserStatList() {
        Map<String, Object> map = new TreeMap<>();
        map.put("terms", Arrays.asList(new String[] { "20190101", "20190201", "20190301", "20190401", "20190501",
                "20190601", "20190701", "20190801", "20190901", "20191001", "20191101", "20191201" }));
        map.put("b", Arrays.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L }));
        List<String> ids = new ArrayList<>();
        ids.add("x");
        ids.add("y");

        when(paasApiRest.getForObject(startsWith("/admin/stats/users"), eq(Map.class))).thenReturn(map);

        Map<?, ?> result = adminStatsService.getUserStatList(ids);
        assertEquals(map, result);
    }

    @Test
    public void getUserList() {
        List<Object> userList = new ArrayList<>();
        User user1 = user("user-01");
        userList.add(user1);
        User user2 = user("user-02");
        userList.add(user2);

        when(paasApiRest.exchange(startsWith("/admin/users/page"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(objectPageResponse);
        when(objectPageResponse.getBody()).thenReturn(objectCustomPage);
        when(objectCustomPage.getContent()).thenReturn(userList);

        CustomPage<Object> result = adminStatsService.getUserList("?nameLike=user");
        assertEquals(userList, result.getContent());
    }

    @Test
    public void getUser() {
        User user1 = user("user-01");

        when(paasApiRest.getForObject(eq("/admin/users/" + userId), eq(Object.class))).thenReturn(user1);

        Object result = adminStatsService.getUser(userId);
        assertEquals(user1, result);
    }

    @Test
    public void getMyTotalList() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);
        List<Instance> instanceList = new ArrayList<>();
        Instance instance1 = instance(1L, software1);
        instanceList.add(instance1);
        Instance instance2 = instance(2L, software1);
        instanceList.add(instance2);

        when(paasApiRest.exchange(startsWith("/instances/page"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(instancePageResponse);
        when(instancePageResponse.getBody()).thenReturn(instanceCustomPage);
        when(instanceCustomPage.getContent()).thenReturn(instanceList);

        CustomPage<Instance> result = adminStatsService.getInstanceListBySwId("?sort=id,asc");
        assertEquals(instanceList, result.getContent());
    }

    @Test
    public void getInstanceListBySwInId() {
        Category category = category(1L, "category-01");
        Software software1 = software(1L, "software-01", category);
        List<Instance> instanceList = new ArrayList<>();
        Instance instance1 = instance(1L, software1);
        instanceList.add(instance1);
        Instance instance2 = instance(2L, software1);
        instanceList.add(instance2);

        when(paasApiRest.exchange(startsWith("/instances/my/totalPage"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(instancePageResponse);
        when(instancePageResponse.getBody()).thenReturn(instanceCustomPage);
        when(instanceCustomPage.getContent()).thenReturn(instanceList);

        CustomPage<Instance> result = adminStatsService.getInstanceListBySwInId("?sort=id,asc");
        assertEquals(instanceList, result.getContent());
    }

    @Test
    public void countOfSoldSw() {
        Category category = category(1L, "category-01");
        List<Software> softwareList = new ArrayList<>();
        Software software1 = software(1L, "software-01", category);
        softwareList.add(software1);
        Software software2 = software(2L, "software-02", category);
        softwareList.add(software2);

        List<Instance> instanceList = new ArrayList<>();
        if (!instanceListEmpty) {
            Instance instance1 = instance(1L, software1);
            instanceList.add(instance1);
            Instance instance2 = instance(2L, software2);
            instanceList.add(instance2);
        }

        when(adminSoftwareService.getAdminSoftwareList(any(String.class))).thenReturn(softwareCustomPage);
        when(softwareCustomPage.getContent()).thenReturn(softwareList);

        when(paasApiRest.exchange(startsWith("/instances/page"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(instancePageResponse);
        when(instancePageResponse.getBody()).thenReturn(instanceCustomPage);
        when(instanceCustomPage.getContent()).thenReturn(instanceList);

        long result = adminStatsService.countOfSoldSw(userId);
        if (!instanceListEmpty) {
            assertEquals(2L, result);
        } else {
            assertEquals(0L, result);
        }
    }

    @Test
    public void countOfSoldSwInstanceListEmpty() {
        instanceListEmpty = true;

        countOfSoldSw();
    }

}