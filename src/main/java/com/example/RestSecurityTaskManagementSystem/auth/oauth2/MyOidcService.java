package com.example.RestSecurityTaskManagementSystem.auth.oauth2;

import com.example.RestSecurityTaskManagementSystem.user.User;
import com.example.RestSecurityTaskManagementSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyOidcService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidc =super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        String name = oidc.getFullName();
        String email=oidc.getEmail();
        String providerId=oidc.getSubject();

        userRepository.findByProviderAndProviderId(provider,providerId)
                .map(existing-> {
                    existing.setName(name);
                    existing.setEmail(email);
                    return userRepository.save(existing);
                }
                ).orElseGet(()->userRepository.save(new User(name,email,provider,providerId)));

        return oidc;
    }
}
