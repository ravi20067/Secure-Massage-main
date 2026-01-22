package com.Security.Secure.Massage.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {

            if (authority.getAuthority().equals("ROLE_PREMIUM")) {
                response.sendRedirect("/premium-dashboard/home");
                return;
            }

            if (authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("OAUTH2_USER")) {
                new DefaultRedirectStrategy()
                        .sendRedirect(request, response, "/dashboard/home");
                return;
            }
        }

        response.sendRedirect("/login?error=true");
    }
}


