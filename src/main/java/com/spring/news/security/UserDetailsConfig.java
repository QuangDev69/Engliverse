//package com.spring.news.security;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//public class UserDetailsConfig {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//    	System.out.println("123456");
//        // Tạo một người dùng mẫu
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("123123"))
//                .roles("USER") // cần phải sử dụng authorities cho quyền chi tiết hơn
//                .build();
//        
//        return new InMemoryUserDetailsManager(user);
//        // Trong sản phẩm thực, bạn sẽ truy vấn từ cơ sở dữ liệu
//    }
//}
