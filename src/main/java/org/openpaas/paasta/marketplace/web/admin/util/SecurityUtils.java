package org.openpaas.paasta.marketplace.web.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SecurityUtils {

    public static OAuth2User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<GrantedAuthority> authorities = new HashSet<>();

        Authentication newAuth = null;

        if (authentication.getClass() == OAuth2AuthenticationToken.class) {
            OAuth2User principal = ((OAuth2AuthenticationToken)authentication).getPrincipal();
            if (principal != null) {
                newAuth = new OAuth2AuthenticationToken(principal, authorities,(((OAuth2AuthenticationToken)authentication).getAuthorizedClientRegistrationId()));
            }
        }

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        Authentication finalAuth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal ::: " + finalAuth.getPrincipal());

        if (finalAuth == null) {
            return null;
        }

        OAuth2User principal = (OAuth2User) finalAuth.getPrincipal();
        System.out.println("user Id ::: " + principal.getAttributes().get("user_name"));

        if (principal == null) {
            return null;
        }

        return principal;
    }

}
