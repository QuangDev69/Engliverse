package com.spring.news.controller;

import com.spring.news.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.spring.news.domain.User;
import com.spring.news.dto.UserDto;
import com.spring.news.service.UserService;


@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordResetService passwordResetService;

	@GetMapping("/login")
	public String login() {
		System.out.println("controller  +++++");

		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("userDto", new UserDto());
		
		return "register"; 
	}

	@PostMapping("/register")
	public String processRegistrationForm(@ModelAttribute("userDto") @Validated UserDto userDto,
			BindingResult bindingResult,  Model model) {

		if (!userDto.getPassword().equals(userDto.getConfirm())) {
			bindingResult.rejectValue("confirm", "error.confirm", "Password and confirm password do not match.");
		}

		User existingUsername = userService.findByUsername(userDto.getUsername());
 		if (existingUsername != null) {
			model.addAttribute("usernameError", "Username already exists.");
			return "register";
		}

		User existingEmail = userService.findByEmail(userDto.getEmail());
		if (existingEmail != null) {
			model.addAttribute("emailError", "Email already exists.");
			return "register";
		}

		if (bindingResult.hasErrors()) {
			return "register"; 
		}

		userService.registerUser(userDto);
		return "redirect:/auth/login"; 
	}

	@GetMapping("/forgotPassword")
	public String showForgotPasswordForm() {
		return "forgot-password";
	}

	@PostMapping("/forgotPassword")
	public String processForgotPasswordForm(HttpServletRequest request, @RequestParam("email") String userEmail) {
		User user = userService.findByEmail(userEmail);
		if (user == null) {
			return "redirect:/auth/forgotPassword?error";
		}
		passwordResetService.sendPasswordResetEmail(user, getAppUrl(request));
		return "redirect:/auth/forgotPassword?success";
	}

	@GetMapping("/resetPassword")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
		String result = passwordResetService.validatePasswordResetToken(token);
		if (result != null) {
			model.addAttribute("message", "Your reset password token is invalid.");
			return "redirect:/auth/login?error=" + result; // Hoặc trang thông báo lỗi tùy chỉnh
		}
		System.out.println("Token in GetMapping resetPassword : "+token);

		model.addAttribute("token", token);
		return "resetPassword";
	}

	@PostMapping("/resetPassword")
	public String processResetPasswordForm(@RequestParam("token") String token,
										   @RequestParam("password") String newPassword, Model model) {
		System.out.println("Token in PostMapping resetPassword : "+token);
		String result = passwordResetService.validatePasswordResetToken(token);
		System.out.println("Result: "+result);
		if (result != null) {
			model.addAttribute("message", "Your reset password token is invalid.");
			return "redirect:/auth/login?error=" + result; // Hoặc trang thông báo lỗi tùy chỉnh
		}
		User user = passwordResetService.getUserByPasswordResetToken(token);
		System.out.println("user: "+user);

		passwordResetService.changeUserPassword(user, newPassword);
		return "redirect:/auth/login?resetSuccess";
	}


	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}


}
