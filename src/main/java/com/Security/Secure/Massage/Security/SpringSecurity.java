package com.Security.Secure.Massage.Security;

import com.Security.Secure.Massage.Service.CustomOAuth2UserService;
import com.Security.Secure.Massage.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurity {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                )
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login/**",
                                "/captcha/**",
                                "/perform_login",
                                "/register/**",
                                "/register",
                                "/perform_register",
                                "/home",
                                "/home/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        .requestMatchers(
                                "/ws/**",
                                "/app/**",
                                "/topic/**",
                                "/queue/**"
                        ).authenticated()
                        .requestMatchers("/premium-dashboard/**").hasRole("PREMIUM")
                        .requestMatchers("/dashboard/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // Your custom login page
                        .loginProcessingUrl("/perform_login") // Form POST URL
                        .successHandler(successHandler)   // After login
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth       // 🔥 THIS enables Google login
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .successHandler(successHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/perform_logout")        // Logout URL
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
