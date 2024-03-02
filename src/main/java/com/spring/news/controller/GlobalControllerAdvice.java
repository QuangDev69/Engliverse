package com.spring.news.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.spring.news.security.CustomUserDetails;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("userId")
    public Integer addUserIdToModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            System.out.println("userId"+userDetails.getUserId());
            return userDetails.getUserId();

        }
        return null;
    }

    @ModelAttribute("isAdmin")
    public boolean  adminModel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("isAdmin"+authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));

            return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        }
        return false;
    }

}