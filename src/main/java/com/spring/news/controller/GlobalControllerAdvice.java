package com.spring.news.controller;

import com.spring.news.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.spring.news.security.CustomUserDetails;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final ImageService imageService;

    @Autowired
    public GlobalControllerAdvice(ImageService imageService) {
        this.imageService = imageService;
    }

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

    @ModelAttribute("imagePath")
    public String globalImagePath(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return imageService.getImagePathByUserId(userDetails.getUserId());
        }
        return "/imageLocal/login.avif";
    }

}