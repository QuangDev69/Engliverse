package com.spring.news.service.serviceImpl;


import com.spring.news.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.news.domain.User;
import com.spring.news.dto.UserDto;
import com.spring.news.repository.UserRepository;
import com.spring.news.service.UserService;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    private  UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public void updateSecurityContextUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList()),
                        user.getUserId(),
                        user.getImagePath()),
                null,
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUserById(int userId) {
    	userRepository.deleteById(userId);
    }


    @Override
    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User updateUser( User user) {
        User existUser = userRepository.findById(user.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        existUser.setUsername(user.getUsername());
        existUser.setEmail(user.getEmail());
        existUser.setPhone(user.getPhone());
        existUser.setSex(user.getSex());
        existUser.setAddress(user.getAddress());
        existUser.setImagePath(user.getImagePath());
        return userRepository.save(existUser);
    }

    public void changeUserPassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }



}
