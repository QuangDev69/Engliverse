package com.spring.news.config;

import com.spring.news.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;



@Configuration
@EnableWebSecurity
public class SecurityConfig   {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@SuppressWarnings("deprecation")
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests(authorizeRequests ->
	                authorizeRequests
	                    .requestMatchers("/auth/**", "/bootstrap/**", "/css/**", "/js/**", "/static/**", "/imageLocal/**", "/webjars/**").permitAll()
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



	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}