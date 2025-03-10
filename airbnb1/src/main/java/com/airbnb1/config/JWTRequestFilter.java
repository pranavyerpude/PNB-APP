package com.airbnb1.config;

import com.airbnb1.Repository.PropertyUserRepository;
import com.airbnb1.Service.JWTService;
import com.airbnb1.entity.PropertyUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

     private JWTService jwtService;
     private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            String userName = jwtService.getUserName(token);
            Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(userName);
           if(opUser.isPresent()){
               PropertyUser propertyUser = opUser.get();

               //To keep track of current User logged in
               UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(propertyUser,null, Collections.singleton(new SimpleGrantedAuthority(propertyUser.getUserRole())));
               authentication.setDetails(new WebAuthenticationDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }


        }

            filterChain.doFilter(request,response);
    }
}
