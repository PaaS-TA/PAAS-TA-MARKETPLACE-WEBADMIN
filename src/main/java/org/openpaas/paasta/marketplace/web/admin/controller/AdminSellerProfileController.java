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
import javax.validation.Valid;

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

    
    @GetMapping(value = "/update/{id}")
    public String getProfilesUpdatePage(Model model, @PathVariable String id) {
        model.addAttribute("types", Profile.Type.values());
        model.addAttribute("status", Profile.Status.values());
        model.addAttribute("profiles", adminSellerProfileService.getProfiles(id));
        return "contents/adminSellerProfile-update";
    }

    @GetMapping(value = "/{id}")
    public String getProfiles(Model model, @PathVariable String id) {
        model.addAttribute("profiles", adminSellerProfileService.getProfiles(id));
        return "contents/adminSellerProfile-detail";
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public Profile updateProfiles(@PathVariable String id, @Valid @RequestBody Profile profile) {
        //model.addAttribute("profiles", adminSellerProfileService.getProfiles(id));
    	return adminSellerProfileService.updateProfiles(id, profile);
        //return "contents/adminSellerProfile-detail";
    }



}
