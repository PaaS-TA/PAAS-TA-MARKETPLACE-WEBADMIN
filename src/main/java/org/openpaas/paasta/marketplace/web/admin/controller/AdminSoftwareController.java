package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.openpaas.paasta.marketplace.api.domain.Yn;
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

@Controller
@RequestMapping(value = "/admin/softwares")
@Slf4j
@RequiredArgsConstructor
public class AdminSoftwareController {

    private final AdminSoftwareService adminSoftwareService;
    private final CommonService commonService;

    @GetMapping
    @ResponseBody
    public CustomPage<Software> getAdminSoftwareList(HttpServletRequest httpServletRequest){
        return adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));
    }

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


    @GetMapping(value = "/{id}")
    public String getAdminSoftware(Model model, @PathVariable Long id) {
        model.addAttribute("software", adminSoftwareService.getAdminSoftwares(id));
        adminSoftwareService.getAdminCategories();
        return "contents/software-detail";
    }


    @GetMapping(value = "/{id}/update")
    public String updateAdminSoftwarPage(Model model, @PathVariable Long id) {
        model.addAttribute("software", adminSoftwareService.getAdminSoftwares(id));
        model.addAttribute("yns", Yn.values());
        model.addAttribute("types", Software.Type.values());
        model.addAttribute("status", Software.Status.values());
        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        return "contents/software-update";
    }


    @PutMapping(value = "/{id}")
    @ResponseBody
    public Software updateAdminSoftware(@PathVariable Long id, @Valid @RequestBody Software software) {
        log.info(">> updateAdminSoftware " + software.toString());
        return adminSoftwareService.updateAdminSoftware(id, software);
    }


}
