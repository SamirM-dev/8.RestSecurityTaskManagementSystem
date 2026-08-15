package com.example.RestSecurityTaskManagementSystem.config;

import com.example.RestSecurityTaskManagementSystem.auth.handller.MyAccessDeniedHandler;
import com.example.RestSecurityTaskManagementSystem.auth.handller.MyEntryPoint;
import com.example.RestSecurityTaskManagementSystem.auth.jwt.JwtTokenFilter;
import com.example.RestSecurityTaskManagementSystem.auth.oauth2.MyFailureHandler;
import com.example.RestSecurityTaskManagementSystem.auth.oauth2.MyOauth2Service;
import com.example.RestSecurityTaskManagementSystem.auth.oauth2.MyOidcService;
import com.example.RestSecurityTaskManagementSystem.auth.oauth2.MySuccessHandler;
import com.example.RestSecurityTaskManagementSystem.details.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final MyAccessDeniedHandler accessDeniedHandler;
    private final MyEntryPoint entryPoint;
    private final MyOauth2Service oauth2Service;
    private final MyOidcService oidcService;
    private final MySuccessHandler successHandler;
    private final MyFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/exchange").permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/tasks/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/tasks/{taskId}/comments/{commentId}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .cors(cors->cors
                        .configurationSource(corsConfigurationSource()))
                .csrf(csrf->csrf
                        .disable())
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers->headers
                        .contentTypeOptions(Customizer.withDefaults())
                        .frameOptions(frame->frame
                                .deny())
                        .httpStrictTransportSecurity(hsts->hsts
                                .preload(true)
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling->handling
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(info->info
                                .userService(oauth2Service)
                                .oidcUserService(oidcService))
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        );
return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider= new  DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setExposedHeaders(List.of("Authorization","X-Request-ID","Content-Type"));
        configuration.setExposedHeaders(List.of("Location"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
