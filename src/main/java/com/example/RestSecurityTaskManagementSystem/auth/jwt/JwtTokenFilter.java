package com.example.RestSecurityTaskManagementSystem.auth.jwt;

import com.example.RestSecurityTaskManagementSystem.details.CustomUserDetailsService;
import com.example.RestSecurityTaskManagementSystem.details.UserPrincipal;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (SecurityContextHolder.getContext().getAuthentication()==null&&token!=null) {
            try {
                UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
                if (jwtTokenProvider.validateToken(token, principal)) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities())
                    );

                }
            }
            catch (IllegalArgumentException | JwtException e){
                log.error("Jwt validation error : {}",e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request,response);
    }


    private String extractToken(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        if (StringUtils.hasText(token)&&token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
