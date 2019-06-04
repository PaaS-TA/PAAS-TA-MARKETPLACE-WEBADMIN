package org.openpaas.paasta.marketplace.web.admin.config.security.userdetail;


import org.cloudfoundry.client.v2.organizations.ListOrganizationsResponse;
import org.cloudfoundry.client.v2.organizations.OrganizationResource;
import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;
import org.cloudfoundry.client.v3.spaces.SpaceResource;
import org.openpaas.paasta.marketplace.web.admin.common.OrgService;
import org.openpaas.paasta.marketplace.web.admin.common.QuotaService;
import org.openpaas.paasta.marketplace.web.admin.common.RestTemplateService;
import org.openpaas.paasta.marketplace.web.admin.common.SpaceService;
import org.openpaas.paasta.marketplace.web.admin.model.Org;
import org.openpaas.paasta.marketplace.web.admin.model.Quota;
import org.openpaas.paasta.marketplace.web.admin.model.QuotaList;
import org.openpaas.paasta.marketplace.web.admin.model.Space;
import org.openpaas.paasta.marketplace.web.admin.security.SsoAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * CustomUserDetails service 클래스.
 *
 * @author hrjin
 * @version 1.0
 * @since 2019.03.22
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${market.place.org.name}")
    private String marketOrgName;

    @Value("${market.place.space.name}")
    private String marketSpaceName;

    @Value("${market.place.quota.name}")
    private String marketQuotaName;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private String token;

    public void setToken(String token){
        this.token = token;
    }

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    public OrgService orgService;

    @Autowired
    public QuotaService quotaService;

    @Autowired
    public SpaceService spaceService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List role = new ArrayList();

        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User("admin", "admin", role);
        return user;
    }

    public UserDetails loadUserBySsoAuthenticationDetails(SsoAuthenticationDetails ssoAuthenticationDetails) {
        List role = new ArrayList();

        LOGGER.info("username : "+ssoAuthenticationDetails.getUserId());
        LOGGER.info("uaaId : "+ssoAuthenticationDetails.getId());
        LOGGER.info("token : "+ssoAuthenticationDetails.getAccessToken());
        LOGGER.info("Instance id : "+ssoAuthenticationDetails.getManagingServiceInstance());

        String username = ssoAuthenticationDetails.getUserId();
        String uaaid = ssoAuthenticationDetails.getId();
        String token = ssoAuthenticationDetails.getAccessToken().toString();
        //String serviceInstanceId = ssoAuthenticationDetails.getManagingServiceInstance();

        String space_guid = "";
        String organization_guid = "";

        String orgName = marketOrgName;
        String spaceName = marketSpaceName;
        String quotaName = marketQuotaName;
        String quotaGuid;
        String existQuotaGuid = "";

        if(username.equals("admin")) {
            // 확인 후 없으면 org & space 생성 cf 호출. 생성 후 리턴으로 orgGuid & spaceGuid 전역변수로 추출할 것.
            if (!orgService.isExistOrgByOrgName(orgName, token)) {
                LOGGER.info("Org 가 존재하지 않습니다.");

                // Org 에 대한 Quota 는 최대치로 부여한다.
                Quota quota = new Quota();
                quota.setName(quotaName);
                quota.setNonBasicServicesAllowed(true);
                quota.setTotalServices(-1);
                quota.setTotalRoutes(-1);
                quota.setTotalReservedRoutePorts(-1);
                quota.setMemoryLimit(102400);
                quota.setInstanceMemoryLimit(-1);
                quota.setAppInstanceLimit(-1);

                Quota orgQuota = new Quota();

                int count = 0;

                QuotaList quotaList = quotaService.getOrgQuotas(token);

                for (Quota q : quotaList.getResources()) {
                    // quota 이름이 같을 경우
                    if (quota.getName().equals(q.getEntity().get("name"))) {
                        LOGGER.info("Exist Quota ::" + q.getMetadata());
                        existQuotaGuid = (String) q.getMetadata().get("guid");
                        count++;
                    }
                }

                if (count > 0) {
                    orgQuota.setOrgQuotaGuid(existQuotaGuid);
                    LOGGER.info("Quota guid ::: " + orgQuota.getOrgQuotaGuid());

                } else {
                    orgQuota = quotaService.createOrgQuota(quota, token);
                    LOGGER.info("created Quota ::: " + orgQuota.toString());
                    quotaGuid = (String) orgQuota.getMetadata().get("guid");
                    orgQuota.setOrgQuotaGuid(quotaGuid);
                }

                Org org = new Org();
                Space space = new Space();

                // orgQuota 가 만들어졌으니 org 생성 준비!!!
                org.setOrgName(orgName);
                org.setQuotaGuid(orgQuota.getOrgQuotaGuid());

                // org 생성
                //Org createdOrg = orgService.createOrg(org);
                Map createdOrg = orgService.createOrg(org, token);
                LOGGER.info("created Org ::: " + createdOrg.toString());

                // Org List 가져온다. 해당 이름 있는 Org 골라서 거기의 orgGuid 를 가져와서 아래 로직을 처리한다.
                ListOrganizationsResponse orgList = orgService.getOrgsList();

                for (OrganizationResource o : orgList.getResources()){
                    // space 이름이 같은 것이 있을 때 space guid 를 부여한다.
                    if (org.getOrgName().equals(o.getEntity().getName())) {
                        organization_guid = o.getMetadata().getId();
                    }
                }

                System.out.println("orgGuid :::::::: " + organization_guid);

                // Admin 일 경우 Org 에 권한 주는 로직
//                UserRole.RequestBody requestBody = new UserRole.RequestBody();
//                requestBody.setUserId(uaaid);
//                requestBody.setRole("OrgManager");
//
//                orgService.associateOrgUserRoles(organization_guid, requestBody);

                if(organization_guid != null && !organization_guid.equals("")){
                    // CF API 에서 필요로 하는 임시 guid
                    space.setGuid(UUID.randomUUID());

                    space.setSpaceName(spaceName);
                    space.setOrgGuid(organization_guid);
                    space.setUserId(uaaid);

                    // space 생성
                    Map createdSpace = spaceService.createSpace(space, token);
                    //CreateSpaceResponse createdSpace = spaceService.createSpaceV3(space);
                    LOGGER.info("created Space ~~~ " + createdSpace.toString());

                    ListSpacesResponse spaceList = spaceService.getSpacesList(organization_guid, token);

                    for (SpaceResource s : spaceList.getResources()){
                        // space 이름이 같은 것이 있을 때 space guid 를 부여한다.
                        if (space.getSpaceName().equals(s.getName())) {
                            space_guid = s.getId();
                        }
                    }
                    System.out.println("space_guid :::::::: " + space_guid);
                }else{
                    LOGGER.info("org guid 가 존재하지 않습니다.");
                }
            } else {
                LOGGER.info("org 가 이미 존재합니다.");
            }
        }
        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User(username, "N/A", role);
        //user.setServiceInstanceId(ssoAuthenticationDetails.getManagingServiceInstance());
        user.setOrganizationGuid(organization_guid);
        user.setSpaceGuid(space_guid);
        return user;
    }


//    public UserDetails loginByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
//
//        Map<String, Object> resBody = new HashMap();
//
//        resBody.put("id", username);
//        resBody.put("password", password);
//        Map result;
//
//
//        try {
//            result = cfJavaClientApiRest.postForObject("/login", resBody, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BadCredentialsException(e.getMessage());
//        }
//        Map info = new HashMap();
//        try {
//            info = marketApiRest.getForObject("/v2/user/" + username, Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Map userInfo = (Map) info.get("User");
//
//        List role = new ArrayList();
//        if (userInfo.get("adminYn").toString().equals("Y")) {
//            role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else {
//            role.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//        User user = new User((String) result.get("id"), (String) result.get("password"), role);
//
//        user.setToken((String) result.get("token"));
//        user.setExpireDate((Long) result.get("expireDate"));
//        user.setName((String) userInfo.get("name"));
//        user.setImgPath((String) userInfo.get("imgPath"));
//
//        return user;
//    }

}
