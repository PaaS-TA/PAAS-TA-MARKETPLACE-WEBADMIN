package org.openpaas.paasta.marketplace.web.admin.controller;

import org.openpaas.paasta.marketplace.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Login 버튼이 있는 페이지 클래스
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), "/oauth2/authorization/cf"));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "contents/login";
    }

    @GetMapping(value = "/index")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        OAuth2AccessToken accessToken = client.getAccessToken();

        User user1 = new User();
        user1.setRole(User.Role.Admin);
        user1.getAuthorities();
//
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, authentication.getPrincipal().getAttributes(), "sub");
        authentication = new OAuth2AuthenticationToken(oAuth2User, authorities, "cf");

        Authentication newAuth = null;

        if (authentication.getClass() == OAuth2AuthenticationToken.class) {
            OAuth2User principal = authentication.getPrincipal();
            if (principal != null) {
                newAuth = new OAuth2AuthenticationToken(principal, authorities,(authentication.getAuthorizedClientRegistrationId()));
            }
        }

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        System.out.println("우헤헤헤 ::: " + authentication.getPrincipal().getAuthorities());
        System.out.println("authentication access Token value ::: " + accessToken.getTokenValue());
        System.out.println("authentication getName ::: " + authentication.getPrincipal().getAttributes().get("user_name"));

        httpSession.setAttribute("yourName", authentication.getPrincipal().getAttributes().get("user_name"));
        httpSession.setAttribute("token", accessToken.getTokenValue());

        return "redirect:/admin/softwares/page";
    }
}
