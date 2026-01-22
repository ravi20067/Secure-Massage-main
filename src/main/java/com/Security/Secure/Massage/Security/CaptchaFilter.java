package com.Security.Secure.Massage.Security;

import com.Security.Secure.Massage.Service.CaptchaService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaService captchaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("/perform_login".equals(request.getServletPath())
                && "POST".equalsIgnoreCase(request.getMethod())) {

            String captcha = request.getParameter("captcha");
            String captchaId = request.getParameter("captchaId");

            if (!captchaService.verifyCaptcha(captchaId, captcha)) {
                response.sendRedirect("/login?captchaError=true");
                return; // 🔥 STOP login
            }
        }

        filterChain.doFilter(request, response);
    }
}

