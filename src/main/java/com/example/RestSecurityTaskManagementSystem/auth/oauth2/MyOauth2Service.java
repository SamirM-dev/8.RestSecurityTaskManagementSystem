package com.example.RestSecurityTaskManagementSystem.auth.oauth2;


import com.example.RestSecurityTaskManagementSystem.user.User;
import com.example.RestSecurityTaskManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyOauth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2 =super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        //Github
        String name = oauth2.getAttribute("name");
        String email= oauth2.getAttribute("email");
        String providerId=String.valueOf(oauth2.getAttribute("id"));

        userRepository.findByProviderAndProviderId(provider,providerId)
                .map(existing -> {
                    existing.setName(name);
                    existing.setEmail(email);
                    return userRepository.save(existing);
                })
                .orElseGet(()->userRepository.save((new User(name,email,provider,providerId))));
        
        return oauth2;
    }
}
