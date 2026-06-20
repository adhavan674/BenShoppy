package com.adhavan.benshoppy.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header  = request.getHeader("authorization");
        String token = null;
        String username=null;
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        if(header!=null && header.startsWith("Bearer")){

        token = header.substring(7);
        username = jwtUtil.extractUserName(token);
        String role = jwtUtil.extractRole(token);
        SimpleGrantedAuthority role1 = new SimpleGrantedAuthority("ROLE_"+role);
        authorityList.add(role1);

        }

        if(username!=null && jwtUtil.tokenValidation(token))  {

            UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(username,null,authorityList);


            // set this user is authenticated in context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

            filterChain.doFilter(request,response);

    }
}
