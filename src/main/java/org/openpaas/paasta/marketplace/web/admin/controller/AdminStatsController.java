package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.*;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminCategoryService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSellerProfileService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSoftwareService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author hrjin
 * @version 1.0
 * @since 2019-08-30
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {

    private final AdminSoftwareService adminSoftwareService;
    private final AdminSellerProfileService adminSellerProfileService;
    private final AdminStatsService adminStatsService;
    private final AdminCategoryService adminCategoryService;
    private final CommonService commonService;

    /**
     * 판매자별 현황 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/sellers")
    public String getSellerStatsMain(Model model, HttpServletRequest httpServletRequest) {
        //model.addAttribute("instanceUserCount", commonService.getJsonStringFromMap(newResult));
        //model.addAttribute("instanceCountSum", adminStatsService.getCountOfInstsUsing());
        CustomPage<Profile> profileList = adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));

        List<String> idIn = new ArrayList<>();
        for (Profile f:profileList.getContent()) {
            idIn.add(f.getId());
        }

        // 승인 상품 수
        Map<String, Long> totalApprovalSwCount = adminStatsService.getCountsOfSwsProvider();
        //model.addAttribute("__profileList", profileList);
        //model.addAttribute("approvalSoftwareCount", totalApprovalSwCount);
        model.addAttribute("approvalSoftwareCount", commonService.getJsonStringFromMap(commonService.getResultMap(idIn, totalApprovalSwCount)));

        // 판매 상품 수



        // 총 판매량
        Map<String, Long> totalSoldResult = adminStatsService.getCountsOfInstanceProvider();
        //model.addAttribute("soldSoftwareCount", totalSoldResult);
        model.addAttribute("soldSoftwareCount", commonService.getJsonStringFromMap(commonService.getResultMap(idIn, totalSoldResult)));


        return "contents/useStatusSeller";
    }

    @GetMapping(value = "/sellers/{id}")
    public String getSellerStats(Model model, @PathVariable String id, HttpServletRequest httpServletRequest) {
        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("sellerStat", adminSellerProfileService.getProfiles(id));

        return "contents/useStatusSellerDetail";
    }

    /**
     * Admin 상품 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/sellers/registSwList")
    @ResponseBody
    public CustomPage<Software> getSoftwaresPerSellerList(HttpServletRequest httpServletRequest){
        return adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));
    }

    /**
     * 상품별 현황 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/softwares")
    public String getSoftwareStatsMain(Model model, HttpServletRequest httpServletRequest) {
        CustomPage<Software> software = adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));

        List<Long> idIn = new ArrayList<>();

        for (Software s:software.getContent()) {
            idIn.add(s.getId());
        }

        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("spec", new SoftwareSpecification());

        Map<Long, Long> result = adminStatsService.getCountsOfInsts(idIn);
        Map newResult = new HashMap();

        for (Long id:idIn) {
            String mapId = "" + id;
            if(result.get(mapId) != null){
                newResult.put(mapId, result.get(mapId));
            }else{
                newResult.put(mapId, 0);
            }
        }
        //사용량 추이
        Map  countsOfInstsProvider =  adminStatsService.countsOfInstsProviderMonthly();
        model.addAttribute("countOfInstsProviderMonthly", countsOfInstsProvider.get("terms"));
        model.addAttribute("countOfInstsProviderCounts", countsOfInstsProvider.get("counts"));

        model.addAttribute("instanceUserCount", commonService.getJsonStringFromMap(newResult));
        model.addAttribute("instanceCountSum", adminStatsService.getCountOfInstsUsing());
        model.addAttribute("instanceUsingUserSum", adminStatsService.getCountOfUsersUsing());
        return "contents/useStatusSoftware";
    }


    /**
     * 상품별 현황 상세 페이지
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/softwares/{id}")
    public String getSoftwareStats(Model model, @PathVariable Long id) {
        model.addAttribute("softwareStat", adminSoftwareService.getAdminSoftwares(id));

        // 단일 상품에 대한 총 사용자 수
        List<Long> idIn = new ArrayList<>();
        idIn.add(id);

        Map<Long, Long> result = adminStatsService.getCountsOfInsts(idIn);
        Iterator iter = result.keySet().iterator();
        Object usedSwCount = null;

        if(result.size() > 0) {
            while(iter.hasNext()){
                Object key = iter.next();
                usedSwCount = result.get(key);
            }
        }else {
            usedSwCount = 0;
        }

        model.addAttribute("usedSwCountSum", usedSwCount);

        return "contents/useStatusSoftwareDetail";
    }

    /**
     * 상품별 현황 상세 페이지 - 판매 현황 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/instances")
    @ResponseBody
    public CustomPage<Instance> getInstanceListBySwId(HttpServletRequest httpServletRequest) {
        return adminStatsService.getInstanceListBySwId(commonService.setParameters(httpServletRequest));
    }

    /**
     * 사용자별 현황메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/users/list")
    public String getUserStatsMain(Model model) {
        // 사용자별 구매 상품 수
        model.addAttribute("instancesCount", commonService.getJsonStringFromMap(adminStatsService.countsOfInstsUser()));
        return "contents/useStatusUser";
    }

    /**
     * 마켓플레이스 전체 사용자 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/users")
    @ResponseBody
    public CustomPage<Object> getUserStatList(HttpServletRequest httpServletRequest) {

        // 전체 사용자 조회
//        List<String> idIn = new ArrayList<>();
//        idIn.add("");
//
//        Map aaa = adminStatsService.getUserStatList(idIn);
//
//        Set<String> set = aaa.keySet();
//        Iterator<String> iter = set.iterator();
//
//        List userAllList = new ArrayList();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        while (iter.hasNext()) {
//            String key = iter.next();
//            Object user = objectMapper.convertValue(aaa.get(key), Object.class);
//            userAllList.add(user);
//        }

        return adminStatsService.getUserList(commonService.setParameters(httpServletRequest));
    }


    /**
     * 사용자별 상세 페이지 이동 및 조회
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/users/{id}")
    public String getUserStats(Model model, @PathVariable String id) {
        model.addAttribute("categories", adminCategoryService.getCategoryList());
        model.addAttribute("userStat", adminStatsService.getUser(id));
        return "contents/useStatusUserDetail";
    }

}
