package learn.service.serviceImpl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import learn.mapper.UserMapper;
import learn.model.User;

@Service // Marks this class as a Spring service component.
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper; // Injects the UserMapper to interact with the database.

    @Override
    // Loads the user details by username.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the User object from the database using the username.
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            // If the user is not found, throw an exception.
            throw new UsernameNotFoundException("User not found");
        }
        // Return the User object, which should implement UserDetails.
        return user;
    }
}
