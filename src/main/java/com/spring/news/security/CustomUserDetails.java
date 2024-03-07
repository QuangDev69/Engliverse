package com.spring.news.security;

import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final int userId;
    private  String imagePath;


    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, int userId, String imagePath) {
        super(username, password, authorities);
        this.userId = userId;
        this.imagePath = imagePath;

    }

    public int getUserId() {
        return userId;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
