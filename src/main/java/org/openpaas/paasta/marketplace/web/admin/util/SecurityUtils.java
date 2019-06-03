package org.openpaas.paasta.marketplace.web.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static Object getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authentication={}", authentication);

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        logger.info("principal={}", principal);

        if (principal == null) {
            return null;
        } else {
        	return principal;
        }
    }

    public static String getUserId() {
        Object user = getUser();

        if (user == null) {
            return null;
        }

        return user.toString();
    }

}
