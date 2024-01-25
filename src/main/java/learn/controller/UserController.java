package learn.controller;

import learn.model.User;
import learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    // Autowire the UserService
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

  
    // Autowire the BCryptPasswordEncoder
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show the registration form
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // Handle the POST request for user registration
    @PostMapping("/register")
    public String registerUser(
    		@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email) {
    	
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);
        userService.createUser(user);
        return "redirect:/login";
    }

    // Show the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    // Autowire the AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    // This method handles the POST request for user login.
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password) {

        // Creates a UsernamePasswordAuthenticationToken with the provided username and password.
        // This token is used to authenticate the user.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // The AuthenticationManager authenticates the token. If the user's credentials are valid,
        // it returns an Authentication object representing the authenticated user.
        Authentication authentication = authenticationManager.authenticate(token);

        // The authenticated user's details are then stored in the SecurityContext.
        // This is how Spring Security keeps track of the current user's authentication status.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // After successful authentication, the user is redirected to the '/users' endpoint.
        // This is typically where the user would be taken to a dashboard or user profile page.
        return "redirect:/users";
    }

    
    // Handle the GET request for '/users' to list all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }  
}
