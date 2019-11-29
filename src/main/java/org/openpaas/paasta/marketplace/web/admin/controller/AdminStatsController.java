package org.openpaas.paasta.marketplace.web.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Instance;
import org.openpaas.paasta.marketplace.api.domain.Profile;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        Map newResult = commonService.getResultMapInsertZero(idIn, result);

        //사용량 추이
        Map  countsOfInstsProvider =  adminStatsService.countsOfInstsProviderMonthlyTransition(idIn);
        model.addAttribute("totalCountInstsProviderInfo", commonService.getJsonStringFromMap(countsOfInstsProvider));
        model.addAttribute("countOfInstsProviderMonthly", countsOfInstsProvider.get("terms"));
        model.addAttribute("countOfInstsProviderCounts", countsOfInstsProvider.get("counts"));

        model.addAttribute("instanceUserCount", commonService.getJsonStringFromMap(newResult));
        model.addAttribute("instanceCountSum", adminStatsService.getCountOfInstsUsing());
        model.addAttribute("instanceUsingUserSum", adminStatsService.getCountOfUsersUsing());

        //판매량(status=Approval,Pending)
        Map<Long, Long> soldPerSwCount = adminStatsService.getSoldInstanceCount(idIn);
        model.addAttribute("soldPerSwCount", commonService.getResultMapInsertZero(idIn, soldPerSwCount));

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
    public String getSoftwareStats(Model model, @PathVariable Long id,  HttpServletRequest httpServletRequest) {
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

        //상품별 현황 상세 그래프 추이
        CustomPage<Software> software = adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));
        List<Long> softwareId = new ArrayList<>();
        for (Software s:software.getContent()) {
            softwareId.add(s.getId());
        }

        Map  countsOfInstsMonthly =  adminStatsService.countsOfInstsProviderMonthlyTransition(idIn);
        model.addAttribute("totalCountsOfInstsMonthlyInfo", commonService.getJsonStringFromMap(countsOfInstsMonthly));
        model.addAttribute("countsOfInstsMonthly", countsOfInstsMonthly.get("terms"));
        model.addAttribute("ccountsOfInstsMonthlyCounts", countsOfInstsMonthly.get("counts"));

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
    public Map<String,Object> getInstanceListBySwId(Model model, HttpServletRequest httpServletRequest) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	
        //사용량 추이
        Map  countsOfInstsProvider =  adminStatsService.countsOfInstsProviderMonthly();
        resultMap.put("countOfInstsProviderMonthly", countsOfInstsProvider.get("terms"));
        resultMap.put("countOfInstsProviderCounts", countsOfInstsProvider.get("counts"));
        
        // 상품별 현황 상세 리스트
        resultMap.put("instanceListBySwId", adminStatsService.getInstanceListBySwId(commonService.setParameters(httpServletRequest)));
        
        // 총금액조회
        resultMap.put("totalPricePerMonth", adminStatsService.getUsagePriceTotal(commonService.setParameters(httpServletRequest)));        
        return resultMap;
    }


    /**
     * 판매자별 현황 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/sellers")
    public String getSellerStatsMain(Model model, HttpServletRequest httpServletRequest) {
        CustomPage<Profile> profileList = adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));

        List<String> idIn = new ArrayList<>();
        for (Profile f:profileList.getContent()) {
            idIn.add(f.getId());
        }

        // 판매자 총 건수(table Num)
        Map<String, Long> totalSoldResult = adminStatsService.getCountsOfInstanceProvider();
        model.addAttribute("soldSoftwareCount", commonService.getJsonStringFromMap(commonService.getResultMap(idIn, totalSoldResult)));

        // 승인상품 수
        Map<String, Long> totalApprovalSwCount = adminStatsService.getCountsOfSwsProvider();
        model.addAttribute("approvalSoftwareCount", commonService.getJsonStringFromMap(commonService.getResultMap(idIn, totalApprovalSwCount)));

        // 판매상품 수
        Map<String, Object> map = new LinkedHashMap<>();
        for (String id:idIn) {
            map.put(id, adminStatsService.countOfSoldSw(id));
        }
        model.addAttribute("getSoldSoftwareCount", commonService.getJsonStringFromMap(map));

        return "contents/useStatusSeller";
    }

    /**
     * 판매자별 현황 상세페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/sellers/{id}")
    public String getSellerStats(Model model, @PathVariable String id, HttpServletRequest httpServletRequest) {
        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("sellerStat", adminSellerProfileService.getProfiles(id));

        // 단일 상품에 대한 총 사용자 수
        CustomPage<Profile> profileList = adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));

        List<String> idIn = new ArrayList<>();
        for (Profile f:profileList.getContent()) {
            idIn.add(f.getId());
        }

        // 판매자 본인의 승인된 총 상품 수
        Map<String, Long> totalApprovalSwCount = adminStatsService.getCountsOfSwsProvider();
        if(totalApprovalSwCount.get(id) != null) {
            model.addAttribute("approvalSwOfProvider", totalApprovalSwCount.get(id));
        } else {
            model.addAttribute("approvalSwOfProvider", 0);
        }

        // 판매 상품 수
        model.addAttribute("mySoldSoftwareCount", adminStatsService.countOfSoldSw(id));

        // 판매량
        Map<String, Long> totalSoldResult = adminStatsService.getCountsOfInstanceProvider();
        if(totalApprovalSwCount.get(id) != null) {
            model.addAttribute("mySoldInstanceCount", totalSoldResult.get(id));
        } else {
            model.addAttribute("mySoldInstanceCount", 0);
        }

        //승인(status = Approval)
        CustomPage<Software> sellerMySoftware = adminSoftwareService.getAdminSoftwareList("?createdBy=" + id);
        if(sellerMySoftware.getTotalElements() > 0) {
            List<Long> softwareId = new ArrayList<>();
            for (Software s:sellerMySoftware.getContent()) {
                softwareId.add(s.getId());
            }

            // 상품별 사용중인 개수
            model.addAttribute("instanceUserCountProvider", adminStatsService.getUsingPerInstanceByProvider(id, softwareId));

            // 상품별 판매량
            Map<Long, Long> soldPerSwCount = adminStatsService.getSoldInstanceCount(softwareId);
            model.addAttribute("soldPerSwCount", commonService.getResultMapInsertZero(softwareId, soldPerSwCount));

            //사용량 추이
            Map  countsOfInstsProvider =  adminStatsService.countsOfInstsProviderMonthlyTransition(softwareId);
            model.addAttribute("totalCountInstsProviderInfo", commonService.getJsonStringFromMap(countsOfInstsProvider));

        } else {
            model.addAttribute("instanceUserCountProvider", null);
            model.addAttribute("soldPerSwCount", null);
            model.addAttribute("totalCountInstsProviderInfo", null);
        }

        return "contents/useStatusSellerDetail";
    }


    /**
     * 사용자별 현황메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/users/list")
    public String getUserStatsMain(Model model, HttpServletRequest httpServletRequest) {
        CustomPage<Profile> profileList = adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));

        List<String> createdBy = new ArrayList<>();

        for (Profile f:profileList.getContent()) {
            createdBy.add(f.getId());
        }

        Map<String, Long> result = adminStatsService.getCountsOfInstsUser(createdBy);
        Map newResult = new HashMap();

        for (String id:createdBy) {
            String mapId = "" + id;
            if(result.get(mapId) != null){
                newResult.put(mapId, result.get(mapId));
            }else{
                newResult.put(mapId, 0);
            }
        }

        //사용량 추이
        Map  countsOfUserProvider =  adminStatsService.countsOfInstsUsersMonthly(createdBy);
        model.addAttribute("totalCountUserProviderInfo", commonService.getJsonStringFromMap(countsOfUserProvider));
        model.addAttribute("countsOfUserProviderMonthly", countsOfUserProvider.get("terms"));
        model.addAttribute("countsOfUserProviderCounts", countsOfUserProvider.get("counts"));

        // 사용자별 구매 상품 수
        model.addAttribute("instancesCount", commonService.getJsonStringFromMap(adminStatsService.countsOfInstsSumUser()));

        return "contents/useStatusUser";
    }


    /**
     * 사용자별 상세 페이지 이동 및 조회
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/users/{id}")
    public String getUserStats(Model model, @PathVariable String id, HttpServletRequest httpServletRequest) {
        model.addAttribute("userStat", adminStatsService.getUser(id));
        model.addAttribute("categories", adminCategoryService.getCategoryList());
        model.addAttribute("spec", new SoftwareSpecification());
        model.addAttribute("status", Software.Status.values());
        model.addAttribute("instancesCount", commonService.getJsonStringFromMap(adminStatsService.countsOfInstsUser()));

        CustomPage<Profile> profileList = adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));
        List<String> idIn = new ArrayList<>();
        for (Profile f:profileList.getContent()) {
            idIn.add(f.getCreatedBy());
        }

        Map<String, Object> map = new LinkedHashMap<>();
        for (String ids:idIn) {
            map.put(ids, adminStatsService.countOfSoldSw(ids));
        }

        // 판매상품 수
        model.addAttribute("getSoldSoftwareCount", commonService.getJsonStringFromMap(map));

        //사용량 추이(12개월 추이)
        Map countsOfUserProvider =  adminStatsService.countsOfInstsSingleUserMonthly(id);
        model.addAttribute("totalCountUserProviderInfo", commonService.getJsonStringFromMap(countsOfUserProvider));

        return "contents/useStatusUserDetail";
    }


    /**
     * 사용자별 상품 상세 페이지(전체상품수)
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/instances/users")
    @ResponseBody
    public CustomPage<Instance> getInstanceListBySwInId(Model model, HttpServletRequest httpServletRequest) {
        return adminStatsService.getInstanceListBySwInId(commonService.setParameters(httpServletRequest));
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
     * 마켓플레이스 전체 사용자 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/users")
    @ResponseBody
    public Map<String,Object> getUserStatList(HttpServletRequest httpServletRequest) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	
    	// 그리드 정보 조회
    	resultMap.put("data", adminStatsService.getUserList(commonService.setParameters(httpServletRequest)));
    	
    	// 사용자별 구매 퍼센트 통계
    	resultMap.put("purchaserPercent", adminStatsService.getPurchaserPercent(commonService.setParameters(httpServletRequest)));
    	
    	// 월별 상품구매 통계
//    	resultMap.put("purchaseTransitionMonth", adminStatsService.getPurchaseTransitionMonth(commonService.setParameters(httpServletRequest)));
    	
    	// 앱사용 사용자 추이
    	resultMap.put("usageTransition", adminStatsService.getUsageTransition(commonService.setParameters(httpServletRequest)));
    	
        return resultMap;
    }
    
    /**
     * 상품현황의 Total 카운트 조회 (판매상품/사용자)
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/softwares/statsInfo")
    @ResponseBody
    public Map<String,Object> softwareStatsInfo(HttpServletRequest httpServletRequest) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();

    	// 현재 사용중인 상품 카운트
    	resultMap.put("countOfInstsUsing", adminStatsService.getCountOfInstsUsing(commonService.setParameters(httpServletRequest)));
    	
    	// 현재 상품을 사용중인 User 카운트
    	resultMap.put("countOfUsersUsing", adminStatsService.getCountOfUsersUsing(commonService.setParameters(httpServletRequest)));

        return resultMap;
    }
    
    /**
     * 상품별현황 > 사용앱, 사용추이 차트 데이터 조회
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/softwares/chart/useAppTransitionInfo")
    @ResponseBody
    public Map<String,Object> useAppTransitionInfo(HttpServletRequest httpServletRequest) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	// 사용앱 차트 데이터 조회
    	resultMap.put("statsUseAppList", adminStatsService.getStatsUseApp(commonService.setParameters(httpServletRequest)));
    	// 사용추이 차트 데이터 조회
    	resultMap.put("statsUseTransitionList", adminStatsService.getStatsUseTransition(commonService.setParameters(httpServletRequest)));
    	return resultMap;
    }
    
    /**
     * 판매자별 현황 > 등록앱, 사용앱 차트 데이터 조회
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/softwares/chart/sellerAppTransitionInfo")
    @ResponseBody
    public Map<String,Object> sellerAppTransitionInfo(HttpServletRequest httpServletRequest) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	// 등록앱 차트 데이터 조회
    	resultMap.put("sellerCreatedAppPercentList", adminStatsService.getSellerCreatedAppPercent(commonService.setParameters(httpServletRequest)));
    	// 사용앱 차트 데이터 조회
    	resultMap.put("sellerCreatedAppTransitionList", adminStatsService.getSellerCreatedAppTransition(commonService.setParameters(httpServletRequest)));
    	return resultMap;
    }

}
