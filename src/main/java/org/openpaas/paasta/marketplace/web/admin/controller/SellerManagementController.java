package org.openpaas.paasta.marketplace.web.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.web.admin.service.SellerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(value = "/sellermgnt")
@RequiredArgsConstructor
public class SellerManagementController {

    @Autowired
    public SellerManagementService sellerManagementService;

    /**
     * 판매자 관리 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping
    public String getAdminProfilesMain() {
        return "contents/sellerManageMain";
    }



}
