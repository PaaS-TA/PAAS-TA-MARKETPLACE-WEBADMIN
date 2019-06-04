package org.openpaas.paasta.marketplace.web.admin.security;


import org.openpaas.paasta.marketplace.web.admin.common.AdminConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomInterceptor 클래스.
 *
 * @author indra
 * @version 1.0
 * @since 2018.08.28
 */
public class SsoAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info("** onAuthenticationFailure in");
        LOGGER.info("REQUEST URL : "+ request.getRequestURL());

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        response.sendRedirect(AdminConstants.MARKET_ADMIN_URL);

        LOGGER.info("** onAuthenticationFailure out");
    }
}
