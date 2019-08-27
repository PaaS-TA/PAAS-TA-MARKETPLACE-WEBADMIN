package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.*;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSoftwareService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/softwares")
@Slf4j
@RequiredArgsConstructor
public class AdminSoftwareController {

    private final AdminSoftwareService adminSoftwareService;
    private final CommonService commonService;

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
        model.addAttribute("software", adminSoftwareService.getAdminSoftwares(id));
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

        return adminSoftwareService.updateAdminSoftware(id, updateSoftware);
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


}
