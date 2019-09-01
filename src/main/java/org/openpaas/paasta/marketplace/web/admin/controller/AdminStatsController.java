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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @GetMapping(value = "/users/list")
    public String getUserStatsMain() {
        return "contents/useStatusUser";
    }

    /**
     * 마켓플레이스 전체 사용자 목록 조회
     *
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/users")
    @ResponseBody
    public CustomPage<Object> getUserStatList(HttpServletRequest httpServletRequest) {

        // 전체 사용자 조회
//        List<String> idIn = new ArrayList<>();
//        idIn.add("");
//
//        Map aaa = adminStatsService.getUserStatList(idIn);
//
//        Set<String> set = aaa.keySet();
//        Iterator<String> iter = set.iterator();
//
//        List userAllList = new ArrayList();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        while (iter.hasNext()) {
//            String key = iter.next();
//            Object user = objectMapper.convertValue(aaa.get(key), Object.class);
//            userAllList.add(user);
//        }

        return adminStatsService.getUserList(commonService.setParameters(httpServletRequest));
    }


    /**
     * 사용자별 상세 페이지 이동 및 조회
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/users/{id}")
    public String getUserStats(Model model, @PathVariable String id) {
        model.addAttribute("userStat", adminStatsService.getUser(id));
        return "contents/useStatusUserDetail";
    }

}
