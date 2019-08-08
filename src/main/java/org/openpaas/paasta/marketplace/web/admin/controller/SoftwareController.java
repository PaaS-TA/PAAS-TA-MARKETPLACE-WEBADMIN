package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.SoftwareService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/softwares")
@Slf4j
@RequiredArgsConstructor
public class SoftwareController {

    private final SoftwareService softwareService;
    private final CommonService commonService;

    /**
     * 상품 목록 조회
     *
     * @param httpServletRequest the httpServletRequest
     * @return CustomPage<Software>
     */
    @GetMapping
    @ResponseBody
    public CustomPage<Software> getSoftwareList(HttpServletRequest httpServletRequest){
        return softwareService.getSoftwareList(commonService.setParameters(httpServletRequest));
    }

    @GetMapping(value = "/page")
    public String getSoftwares(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpSession httpSession, SoftwareSpecification spec, Authentication authentication) {
//        httpSession.setAttribute("yourName", oauth2User.getAttributes().get("user_name"));
        model.addAttribute("categories", softwareService.getCategories());
        model.addAttribute("spec", new SoftwareSpecification());
        model.addAttribute("status", Software.Status.values());

        return "contents/software-list";
    }

    /*
    @GetMapping(value = "/{id}")
    public String getSoftware(Model model, @PathVariable Long id) {
        model.addAttribute("software", softwareService.getSoftware(id));
        softwareService.getCategories();
        return "contents/software-detail";
    }
    */

}
