package com.spring.news.service;
import com.spring.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final UserRepository userRepository;

    @Autowired
    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getImagePathByUserId(Integer userId) {
        return userRepository.findImagePathByUserId(userId);
    }
}
