package com.spring.news.service;

import com.spring.news.domain.PasswordResetToken;
import com.spring.news.domain.User;
import com.spring.news.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUser(user);
        myToken.setToken(token);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)); // 24h
        tokenRepository.save(myToken);
    }

    public void sendPasswordResetEmail(User user, String appUrl) {
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        String resetUrl = appUrl + "/auth/resetPassword?token=" + token;
        emailService.sendEmail(user.getEmail(), "Password Reset Request", "To reset your password, click the link below:\n" + resetUrl);
    }

    public User getUserByPasswordResetToken(String token) {
        System.out.println("Token: "+token);
        if (token == null) {
            System.out.println("Token: "+token);
            return null; // hoặc thực hiện xử lý khác nếu cần
        }
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);
        System.out.println("passwordResetToken: "+passwordResetToken);

        if (passwordResetToken != null) {
            return passwordResetToken.getUser();
        }
        return null; // hoặc thực hiện xử lý khác tùy thuộc vào yêu cầu của ứng dụng
    }
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalidToken";
        }
        // Thêm các kiểm tra khác như kiểm tra hết hạn của token
        return null;
    }

    public void changeUserPassword(User user, String newPassword) {
        userService.changeUserPassword(user, newPassword);
    }
}
