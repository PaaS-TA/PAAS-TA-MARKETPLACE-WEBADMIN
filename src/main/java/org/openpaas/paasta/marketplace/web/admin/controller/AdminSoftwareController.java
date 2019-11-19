package org.openpaas.paasta.marketplace.web.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareHistory;
import org.openpaas.paasta.marketplace.api.domain.SoftwarePlan;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.openpaas.paasta.marketplace.api.domain.TestSoftwareInfo;
import org.openpaas.paasta.marketplace.api.domain.Yn;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSoftwareService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/admin/softwares")
@Slf4j
@RequiredArgsConstructor
public class AdminSoftwareController {

    private final AdminSoftwareService adminSoftwareService;
    private final CommonService commonService;

    @Value("${cloudfoundry.cc.api.host}")
    private String cloudfoundryApiHost;
    
    /**
     * Admin 상품 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping
    @ResponseBody
    public CustomPage<Software> getAdminSoftwareList(HttpServletRequest httpServletRequest){
        return adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));
    }


    /**
     * Admin 상품 목록 페이지 이동
     *
     * @param model
     * @param oauth2User
     * @param httpSession
     * @param spec
     * @param authentication
     * @return
     */
    @GetMapping(value = "/page")
    public String getAdminSoftwares(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpSession httpSession, SoftwareSpecification spec, Authentication authentication) {
//        httpSession.setAttribute("yourName", oauth2User.getAttributes().get("user_name"));
        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("spec", new SoftwareSpecification());
        model.addAttribute("status", Software.Status.values());
        model.addAttribute("yns", Yn.values());
        model.addAttribute("statusApprove", Software.Status.Approval);
        return "contents/software-list";
    }


    /**
     * Admin 상품 페이지 이동 및 조회
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public String getAdminSoftware(Model model, @PathVariable Long id) {
    	model.addAttribute("cloudfoundryApiHost", cloudfoundryApiHost);
        model.addAttribute("software", adminSoftwareService.getAdminSoftwares(id));
        model.addAttribute("softwarePlanList", adminSoftwareService.getSoftwarePlanList(id));
        adminSoftwareService.getAdminCategories();
        return "contents/software-detail";
    }


    /**
     * Admin 상품 수정 페이지 이동
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/update")
    public String updateAdminSoftwarPage(Model model, @PathVariable Long id) {
        model.addAttribute("software", adminSoftwareService.getAdminSoftwares(id));
        model.addAttribute("yns", Yn.values());
        model.addAttribute("types", Software.Type.values());
        model.addAttribute("status", Software.Status.values());
        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("softwarePlanList", adminSoftwareService.getSoftwarePlanList(id));
        return "contents/software-update";
    }


    /**
     * Admin 상품 수정
     *
     * @param id
     * @param software
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public Software updateAdminSoftware(@PathVariable Long id, @Valid @RequestBody Software software) {
        log.info(">> updateAdminSoftware " + software.toString());
        Software updateSoftware = adminSoftwareService.getAdminSoftwares(id);
        updateSoftware.setCategory(software.getCategory());
        updateSoftware.setInUse(software.getInUse());
        updateSoftware.setStatus(software.getStatus());
        updateSoftware.setConfirmComment(software.getConfirmComment());
        updateSoftware.setHistoryDescription(software.getHistoryDescription());

        return adminSoftwareService.updateAdminSoftware(id, updateSoftware);
    }

    /**
     * Admin 상품 판매가격 수정이력 조회
     *
     * @param id
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/plan/{id}/histories")
    @ResponseBody
    public List<SoftwarePlan> getPlanHistoryList(@NotNull @PathVariable Long id, HttpServletRequest httpServletRequest) {
        return adminSoftwareService.getPlanHistoryList(id, commonService.setParameters(httpServletRequest));
    }

    /**
     * Admin 상품 판매금액 적용월 검색조회
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/plan/{id}/applyMonth")
    @ResponseBody
    public List<SoftwarePlan> getApplyMonth(@NotNull @PathVariable Long id,@RequestParam (name="applyMonth") String applyMonth) {
        return adminSoftwareService.getApplyMonth(id, applyMonth);
    }

    /**
     * Admin 상품 수정이력 조회
     *
     * @param id
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/{id}/histories")
    @ResponseBody
    public List<SoftwareHistory> getHistoryList(@NotNull @PathVariable Long id, HttpServletRequest httpServletRequest) {
        return adminSoftwareService.getHistoryList(id, commonService.setParameters(httpServletRequest));
    }


    /**
     * 앱 배포 테스트
     *
     * @param id
     * @param planId
     * @param testSoftwareInfo
     * @return
     */
    @PostMapping(value = "/{id}/plan/{planId}")
    @ResponseBody
    public TestSoftwareInfo deployTestSoftware(@PathVariable Long id, @PathVariable Long planId, @RequestBody TestSoftwareInfo testSoftwareInfo) {
        return adminSoftwareService.deployTestSoftware(id, planId, testSoftwareInfo);
    }


    /**
     * 배포 테스트한 앱 목록 조회
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/testSwInfo")
    @ResponseBody
    public List<TestSoftwareInfo> getTestSwInfoList(@PathVariable Long id) {
        return adminSoftwareService.getAdminTestSwInfoList(id);
    }


    /**
     * 배포 테스트한 앱 최근 로그
     *
     * @param appGuid
     * @return
     */
    @GetMapping(value = "/app/{appGuid}")
    @ResponseBody
    public Map getRecentAppLog(@PathVariable String appGuid) {
        return adminSoftwareService.getRecentAppLog(appGuid);
    }


    /**
     * 배포 테스트한 앱 삭제
     *
     * @param appGuid
     */
    @DeleteMapping(value = "/{swId}/testSwInfo/{id}/app/{appGuid}")
    @ResponseBody
    public Map deleteDeployTestApp(@PathVariable Long swId, @PathVariable Long id, @PathVariable String appGuid) {
        return adminSoftwareService.deleteDeployTestApp(swId, id, appGuid);
    }
}
