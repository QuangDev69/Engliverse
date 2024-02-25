package com.spring.news.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@SuppressWarnings("deprecation")
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            // CSRF protection is enabled by default, no need to explicitly enable it
	            .authorizeRequests(authorizeRequests -> 
	                authorizeRequests
	                    .requestMatchers("/auth/**", "/bootstrap/**", "/css/**", "/js/**", "/imageLocal/**", "/webjars/**").permitAll()
	                    .anyRequest().authenticated()
	            )
	            .formLogin(formLogin -> 
	                formLogin
	                    .loginPage("/auth/login")
	                    .loginProcessingUrl("/auth/login")
	                    .defaultSuccessUrl("/courses/all", true)
	                    .failureUrl("/auth/login?error=true")
	                    .permitAll()
	            )
	            .logout(logout -> 
	                logout
	                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
	                    .logoutSuccessUrl("/auth/login?logout")
	                    .invalidateHttpSession(true)
	                    .deleteCookies("JSESSIONID")
	                    .clearAuthentication(true)
	                    .permitAll()
	            );
	        return http.build();
	    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}