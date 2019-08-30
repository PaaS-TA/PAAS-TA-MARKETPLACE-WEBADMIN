package org.openpaas.paasta.marketplace.web.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.openpaas.paasta.marketplace.web.admin.common.CommonService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSoftwareService;
import org.openpaas.paasta.marketplace.web.admin.service.AdminStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final AdminStatsService adminStatsService;
    private final CommonService commonService;

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
    public String getSoftwareStatsMain(Model model, HttpServletRequest httpServletRequest) {
        CustomPage<Software> software = adminSoftwareService.getAdminSoftwareList(commonService.setParameters(httpServletRequest));

        List<Long> idIn = new ArrayList<>();

        for (Software s:software.getContent()) {
            idIn.add(s.getId());
        }

        model.addAttribute("categories", adminSoftwareService.getAdminCategories());
        model.addAttribute("spec", new SoftwareSpecification());

        Map<Long, Long> result = adminStatsService.getCountsOfInsts(idIn);
        Map newResult = new HashMap();

        for (Long id:idIn) {
            String mapId = "" + id;
            if(result.get(mapId) != null){
                newResult.put(mapId, result.get(mapId));
            }else{
                newResult.put(mapId, 0);
            }
        }

        model.addAttribute("instanceUserCount", commonService.getJsonStringFromMap(newResult));
        model.addAttribute("instanceCountSum", adminStatsService.getCountOfInstsUsing());
        model.addAttribute("instanceUsingUserSum", adminStatsService.getCountOfUsersUsing());
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
    public String getSoftwareStats(Model model, @PathVariable Long id) {
        model.addAttribute("softwareStat", adminSoftwareService.getAdminSoftwares(id));
        return "contents/useStatusSoftwareDetail";
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
