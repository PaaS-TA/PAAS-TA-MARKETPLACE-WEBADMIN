package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.openpaas.paasta.marketplace.web.admin.service.SoftwareService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/softwares")
@Slf4j
@RequiredArgsConstructor
public class SoftwareController {

    private final SoftwareService softwareService;

    @GetMapping
    public String getSoftwares(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpSession httpSession, SoftwareSpecification spec, Authentication authentication) {
//        httpSession.setAttribute("yourName", oauth2User.getAttributes().get("user_name"));
        model.addAttribute("categories", softwareService.getCategories());
        model.addAttribute("spec", new SoftwareSpecification());
        model.addAttribute("softwares", softwareService.getSoftwares(spec).getContent());
        return "contents/software-list";
    }

    @GetMapping(value = "/{id}")
    public String getSoftware(Model model, @PathVariable Long id) {
        model.addAttribute("software", softwareService.getSoftware(id));
        softwareService.getCategories();
        return "contents/software-detail";
    }

//    @GetMapping(value = "/{id}/update")
//    public String updateSoftwareHtml(Model model, @PathVariable Long id) {
//        model.addAttribute("software", softwareService.getSoftware(id));
//        model.addAttribute("types", Type.values());
//        model.addAttribute("yns", Yn.values());
//        model.addAttribute("categories", softwareService.getCategories());
//        return "contents/software-update";
//    }

    @PutMapping(value = "/{id}")
    public String updateSoftware(@PathVariable Long id, @Valid Software software) {
        softwareService.updateSoftware(software);
        return "redirect:/softwares/" + id;
    }

//    @GetMapping(value = "/create")
//    public String createSoftwareHtml(Model model, HttpSession httpSession, @ModelAttribute Software software) {
//        model.addAttribute("types", Type.values());
//        model.addAttribute("yns", Yn.values());
//        model.addAttribute("categories", softwareService.getCategories());
//        return "contents/software-create";
//    }

    @PostMapping
    public String createSoftware(@Valid Software software, BindingResult bindingResult, @RequestParam(value = "screenshots") MultipartFile[] screenshots, @RequestParam(value = "iconFile") MultipartFile iconFile,
                                 @RequestParam(value = "productFile") MultipartFile productFile, @RequestParam(value = "environmentFile") MultipartFile environmentFile) {
        if (bindingResult.hasErrors()) {
            return "contents/software-create";
        }

        log.info("소프토 웨아! {}", software);
        software.setIcon(iconFile.getOriginalFilename());
        software.setApp(productFile.getOriginalFilename());
        software.setManifest(environmentFile.getOriginalFilename());
//        software.setScreenshotList();

        softwareService.createSoftware(software);
        return "redirect:/softwares";
    }
}
