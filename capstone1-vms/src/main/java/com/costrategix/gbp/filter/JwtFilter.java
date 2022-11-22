package com.costrategix.gbp.filter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.costrategix.gbp.config.JwtUtil;
import com.costrategix.gbp.controller.ForgetPasswordController;
import com.costrategix.gbp.entity.MultiFactorRequest;
import com.costrategix.gbp.entity.User;
import com.costrategix.gbp.repository.MultiFactorRequestRepo;
import com.costrategix.gbp.repository.UserRepo;
import com.costrategix.gbp.service.CustomUserDetailsService;
import com.costrategix.gbp.service.ForgetPasswordService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService service;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ForgetPasswordController forgetPasswordController;
    


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String email = null;
        int userId = 0; 
        if (authorizationHeader != null && authorizationHeader.startsWith("")) {
            token = authorizationHeader.substring(0);
            email = jwtUtil.extractUsername(token);
  
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(email);
            
            if (jwtUtil.validateToken(token, userDetails)) {
            	
		         UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
		                 new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		         usernamePasswordAuthenticationToken
		               	 .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
    