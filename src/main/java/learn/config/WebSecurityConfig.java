package learn.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // Marks this class as a configuration class for Spring.
@EnableWebSecurity // This annotation enables the Spring Security's web security support.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    // Configure the HTTP security for the web application.
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Only authenticated users can access the "/users" path.
                .antMatchers("/users").authenticated() 
                // Allow access to all other paths without authentication.
                .anyRequest().permitAll()
            .and()
            .formLogin()
                // Specify the login page path.
                .loginPage("/login")
                // After successful login, redirect to "/users".
                .defaultSuccessUrl("/users") 
                .permitAll()
            .and()
            .logout()
                // Allow logout for all users.
                .permitAll();
    }

    @Bean
    @Override
    // Expose the AuthenticationManager as a bean to be used in other parts of the application.
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    // Configure the authentication mechanism.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Use UserDetailsService to authenticate users from the database.
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    // Define a BCryptPasswordEncoder bean to be used for password encoding.
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    // Configure the global authentication manager.
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Set up the UserDetailsService with the password encoder.
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
