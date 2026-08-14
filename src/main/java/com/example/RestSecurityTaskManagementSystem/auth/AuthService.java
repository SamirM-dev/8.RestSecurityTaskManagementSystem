package com.example.RestSecurityTaskManagementSystem.auth;

import com.example.RestSecurityTaskManagementSystem.auth.dto.LoginRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.RefreshTokenRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.RegisterRequest;
import com.example.RestSecurityTaskManagementSystem.auth.dto.TokenResponse;
import com.example.RestSecurityTaskManagementSystem.auth.jwt.JwtTokenProvider;
import com.example.RestSecurityTaskManagementSystem.auth.jwt.RefreshToken;
import com.example.RestSecurityTaskManagementSystem.auth.jwt.RefreshTokenRepository;
import com.example.RestSecurityTaskManagementSystem.details.CustomUserDetailsService;
import com.example.RestSecurityTaskManagementSystem.details.UserPrincipal;
import com.example.RestSecurityTaskManagementSystem.exception.ResourceAlreadyExistException;
import com.example.RestSecurityTaskManagementSystem.user.User;
import com.example.RestSecurityTaskManagementSystem.user.UserRepository;
import com.example.RestSecurityTaskManagementSystem.user.UserService;
import com.example.RestSecurityTaskManagementSystem.user.dto.UserResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final String TOKEN_TYPE="Bearer";

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse register(RegisterRequest request){
        String email = request.email();
        if (userRepository.existByEmail(email)){
            throw new ResourceAlreadyExistException("User with email \""+email+"\" already exist");
        }
        return userService.toResponse(userRepository.save(new User(request.name(),email,passwordEncoder.encode(request.password()))));
    }

    public TokenResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );
        UserPrincipal principal =(UserPrincipal) userDetailsService.loadUserByUsername(request.email());
        String accessToken = jwtTokenProvider.generateAccessToken(principal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal);

        RefreshToken refreshToken1 = new RefreshToken(refreshToken,principal.getUser(), LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken1);
        principal.getUser().addRefreshToken(refreshToken1);

        return toResponse(accessToken,refreshToken);
    }

    public TokenResponse refresh(RefreshTokenRequest request){
        RefreshToken token = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(()->new JwtException("Token does not found"));
        if (jwtTokenProvider.isTokenExpired(token.getToken())){
            refreshTokenRepository.delete(token);
            token.getUser().deleteRefreshToken(token);
            throw new JwtException("Token was expired");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(new UserPrincipal(token.getUser()));
        String refreshToken = jwtTokenProvider.generateRefreshToken(new UserPrincipal(token.getUser()));

        token.setToken(refreshToken);
        token.setExpiresAt(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(token);

        return toResponse(accessToken,refreshToken);

    }

    public void logout(RefreshTokenRequest request){
        RefreshToken token = refreshTokenRepository.findByToken(request.refreshToken()).orElseThrow(()->new JwtException("Token does not found"));
        token.getUser().deleteRefreshToken(token);
        refreshTokenRepository.delete(token);
    }

    public TokenResponse exchange(){

    }

    public UserResponse me(UserPrincipal principal){
        return userService.toResponse(principal.getUser());
    }

    public TokenResponse toResponse(String accessToken,String refreshToken){
        return new TokenResponse(
                accessToken,refreshToken,TOKEN_TYPE
        );
    }




}
