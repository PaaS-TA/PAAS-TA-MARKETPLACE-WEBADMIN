package org.openpaas.paasta.marketplace.web.admin.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unchecked")
public class AdminSellerProfileServiceTest extends AbstractMockTest {

    AdminSellerProfileService adminSellerProfileService;

    @Mock
    ResponseEntity<CustomPage<Profile>> profilePageResponse;

    @Mock
    CustomPage<Profile> profileCustomPage;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        adminSellerProfileService = new AdminSellerProfileService(paasApiRest);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    // FIXME: Check used method
    public void getProfiles() {
        Map<Object, Object> map = new TreeMap<>();

        when(paasApiRest.getForObject(startsWith("/admin/profiles/page"), eq(Map.class))).thenReturn(map);

        Map<?, ?> result = adminSellerProfileService.getProfiles();
        assertEquals(map, result);
    }

    @Test(expected = RuntimeException.class)
    // FIXME: Check used method
    public void getProfilesError() {
        when(paasApiRest.getForObject(startsWith("/admin/profiles/page"), eq(Map.class)))
                .thenThrow(new IllegalStateException());

        adminSellerProfileService.getProfiles();
    }

    @Test
    public void getProfileList() {
        List<Profile> profileList = new ArrayList<>();
        Profile profile1 = profile("user-01");
        profileList.add(profile1);
        Profile profile2 = profile("user-02");
        profileList.add(profile2);

        when(paasApiRest.exchange(startsWith("/admin/profiles/page"), eq(HttpMethod.GET), eq(null),
                any(ParameterizedTypeReference.class))).thenReturn(profilePageResponse);

        when(profilePageResponse.getBody()).thenReturn(profileCustomPage);
        when(profileCustomPage.getContent()).thenReturn(profileList);

        CustomPage<Profile> result = adminSellerProfileService.getProfileList("?nameLike=user");
        assertEquals(profileList, result.getContent());
    }

    @Test
    public void getProfiles2() {
        Profile profile1 = profile(userId);

        when(paasApiRest.getForObject(startsWith("/admin/profiles/" + userId), eq(Profile.class))).thenReturn(profile1);

        Profile result = adminSellerProfileService.getProfiles(userId);
        assertEquals(profile1, result);
    }

    @Test
    public void updateProfiles() {
        Profile profile1 = profile(userId);

        doNothing().when(paasApiRest).put(eq("/admin/profiles/" + userId), any(Profile.class));

        when(paasApiRest.getForObject(startsWith("/admin/profiles/" + userId), eq(Profile.class))).thenReturn(profile1);

        Profile result = adminSellerProfileService.updateProfiles(userId, profile1);
        assertEquals(profile1, result);
    }

}
