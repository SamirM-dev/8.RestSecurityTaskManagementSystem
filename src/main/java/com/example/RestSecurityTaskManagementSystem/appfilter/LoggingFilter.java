package com.example.RestSecurityTaskManagementSystem.appfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        String method=request.getMethod();
        String uri=request.getRequestURI();
        String query = request.getQueryString();
        String fullUri=query==null?uri:uri+"?"+query;
        log.info("-> {} {}",method,fullUri);

        try {
            filterChain.doFilter(request,response);
        }
        finally {
            log.info("<- {} {} | {} | {} ms",method,fullUri,response.getStatus(),System.currentTimeMillis()-startTime);
        }
    }
}
