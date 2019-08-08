package org.openpaas.paasta.marketplace.web.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openpaas.paasta.marketplace.api.domain.*;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSellerProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(value = "/admin/profiles")
@RequiredArgsConstructor
public class AdminSellerProfileController {

    public final AdminSellerProfileService adminSellerProfileService;
    private final CommonService commonService;


    @GetMapping
    @ResponseBody
    public CustomPage<Profile> getProfileList(HttpServletRequest httpServletRequest){
        return adminSellerProfileService.getProfileList(commonService.setParameters(httpServletRequest));
    }


    @GetMapping(value = "/page")
    public String getPage(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpSession httpSession, ProfileSpecification spec, Authentication authentication) {
//        httpSession.setAttribute("yourName", oauth2User.getAttributes().get("user_name"));
        model.addAttribute("profiles",adminSellerProfileService.getProfiles());
        model.addAttribute("spec", new ProfileSpecification());
        model.addAttribute("type", Profile.Type.values());
        return "contents/adminSellerProfile-list";
    }


    @GetMapping(value = "/{id}")
    public String getProfiles(Model model, @PathVariable String id) {
        model.addAttribute("profiles", adminSellerProfileService.getProfiles(id));
        return "contents/adminSellerProfile-detail";
    }

    @PutMapping(value = "/{id}")
    public String updateProfiles(Model model, @PathVariable String id) {
        model.addAttribute("profiles", adminSellerProfileService.updateProfiles(id));
        return "contents/adminSellerProfile-detail";
    }




}
