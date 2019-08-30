package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 판매자별 현황 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/sellers")
    public String getSellerStatsMain() {
        return "contents/useStatusSeller";
    }


    /**
     * 상품별 현황 메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/softwares")
    public String getSoftwareStatsMain() {
        return "contents/useStatusSoftware";
    }


    /**
     * 사용자별 현황메인페이지로 이동한다.
     *
     * @return ModelAndView(Spring 클래스)
     */
    @GetMapping(value = "/users")
    public String getUserStatsMain() {
        return "contents/useStatusUser";
    }
}
