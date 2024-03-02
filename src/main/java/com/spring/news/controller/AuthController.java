package com.spring.news.controller;

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

}
