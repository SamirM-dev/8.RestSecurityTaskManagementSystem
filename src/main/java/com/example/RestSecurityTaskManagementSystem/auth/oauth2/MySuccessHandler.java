package com.example.RestSecurityTaskManagementSystem.auth.oauth2;

import com.example.RestSecurityTaskManagementSystem.helper.OneStoreService;
import com.example.RestSecurityTaskManagementSystem.user.User;
import com.example.RestSecurityTaskManagementSystem.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MySuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final OneStoreService oneStoreService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        User user = userRepository.findByProviderAndProviderId(auth2AuthenticationToken.getAuthorizedClientRegistrationId(),extractProviderId(auth2AuthenticationToken.getPrincipal())).orElseThrow(EntityNotFoundException::new);

        String code = oneStoreService.generate(user.getId());

        response.sendRedirect("http://localhost:3000/oauth2/callback?code="+code);
    }

    private String extractProviderId(OAuth2User oauth){
        if (oauth instanceof OidcUser oidc){
            return oidc.getSubject();
        }
        return oauth.getAttribute("id");
    }
}
