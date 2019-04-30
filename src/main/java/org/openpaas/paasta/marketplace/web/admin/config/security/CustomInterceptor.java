package org.openpaas.paasta.marketplace.web.admin.config.security;//package org.openpaas.paasta.marketplace.web.config.security;
//
//import org.openpaas.paasta.marketplace.web.common.Constants;
////import org.openpaas.paasta.marketplace.web.common.RestTemplateService;
//import org.openpaas.paasta.marketplace.web.config.security.userdetail.CustomUserDetailsService;
//import org.openpaas.paasta.marketplace.web.security.SsoAuthenticationDetails;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * CustomInterceptor 클래스.
// *
// * @author indra
// * @version 1.0
// * @since 2018.08.28
// */
//public class CustomInterceptor extends HandlerInterceptorAdapter {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomInterceptor.class);
//
////    @Autowired
////    RestTemplateService restTemplateService;
//
//    @Autowired
//    CustomUserDetailsService customUserDetailsService;
//
//    /**
//     * preHandle 구현
//     * 최초 접속시 redirect 및 namespace 체크
//     *
//     * @param request    HttpServletRequest
//     * @param response   HttpServletResponse
//     * @param handler    Object
//     * @return boolean
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String url = request.getRequestURI();
//        String ajaxHeader = request.getHeader("X-CaaS-Ajax-call"); // Ajax Call header value
//
//        LOGGER.info("### Interceptor start ###");
//        LOGGER.info("** Request URI - "+url);
//
//        try {
//            if (!url.contains("/common/error/unauthorized") && !url.contains("/resources")) {
//
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                SsoAuthenticationDetails ssoAuthenticationDetails = null;
//
//                boolean isInitUrl      = url.contains(Constants.MARKET_INIT_URL); // 초기화 URL 여부
//                boolean isUnauthorized = false; // author 참조 에러 : true  정상 : false
//
//                try {
//
//                    // Default. 권한 정보 체크
//                    if (authentication != null) {
//                        ssoAuthenticationDetails = (SsoAuthenticationDetails) authentication.getDetails();
//                    } else { // authentication == null
//                        LOGGER.error("== authentication info is null.");
//                        isUnauthorized = true; // authentication 정보 없음
//                    }
//
//
//                    // C. A/B의 isUnauthorized 분기 처리
//                    if(isUnauthorized){ // 권한 참고 실패
//
//                        LOGGER.info(url+" == Author check is Failure.");
//
//                        if(ajaxHeader == null) { // Ajax call 외에는 redirect 처리
//                            response.sendRedirect(request.getContextPath()+"/common/error/unauthorized");
//                        }else{ // Ajax call 일시 errorCode 전송
//
//                            if("true".equals(ajaxHeader)){ // header value check
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                            }else{
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED
//                                        , "Header value 'X-CaaS-Ajax-call' is mismatch.");
//                            }
//                        }
//
//                    }else{ // 정상 처리
//                        LOGGER.info(url+" == Author check is Passed.");
//
//                        if(isInitUrl){ // 최초 접속 일시 redirect 처리
//                            response.sendRedirect(Constants.MARKET_INIT_URL);
//                        }else{ // 그외 핸들링 처리 없음
//
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    response.sendRedirect(request.getContextPath()+"/common/error/unauthorized");
//                }
//
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//
//            response.sendRedirect(request.getContextPath()+"/common/error/unauthorized");
//            return false;
//        }
//
//        LOGGER.info("### Intercepter end ###");
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
//    }
//
//}
