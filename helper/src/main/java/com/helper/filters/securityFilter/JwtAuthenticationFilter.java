package com.helper.filters.securityFilter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.helper.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil,
                                   UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String header=request.getHeader("Authorization"); 
        String jwtToken=null;
        String username=null;
        System.out.println(header);
        if(header!=null && header.startsWith("Bearer "))
        {
            jwtToken=header.substring(7);
            username=jwtUtil.getUsernameFromToken(jwtToken);

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                                                                                userDetails, 
                                                                                                null,
                                                                                                userDetails.getAuthorities()
                                                                                            );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
        
    }

}
