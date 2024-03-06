package com.spring.news.service;

import com.spring.news.domain.User;
import com.spring.news.dto.UserDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	void addUser(User user);

	public List<User> getAllUsers();

    void registerUser(UserDto userDto);
    
    void deleteUserById(int userId);

    User getUserById(int userId);

    User findByUsername(String username);
    
    User findByEmail(String email);
    
    Page<User> findAll(Pageable pageable);

    User updateUser(User user);

    void changeUserPassword(User user, String newPassword);
}
