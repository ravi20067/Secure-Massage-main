package com.Security.Secure.Massage.Service;

import com.Security.Secure.Massage.Entity.User;
import com.Security.Secure.Massage.Enums.AuthType;
import com.Security.Secure.Massage.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request)
            throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();

        OAuth2User oauthUser = delegate.loadUser(request);

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // 🔥 FIRST TIME → SIGN UP
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setAuthType(AuthType.GOOGLE);
            user.getRoles().add("ROLE_USER");
            userRepository.save(user);

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new DefaultOAuth2User(
                    authorities,
                    oauthUser.getAttributes(),
                    "email"
            );
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return new DefaultOAuth2User(
                authorities,
                oauthUser.getAttributes(),
                "email"
        );
    }
}

