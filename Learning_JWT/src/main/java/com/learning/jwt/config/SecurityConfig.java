package com.learning.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import com.learning.jwt.entity.Permission;
import com.learning.jwt.entity.Role;
import com.learning.jwt.repository.UserRepo;
import com.learning.jwt.service.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	 UserRepo userRepo;
	@Autowired
	@Lazy
	 AuthenticationProvider authenticationProvider;
	@Autowired
	 JwtAuthFilter jwtAuthFilter;
	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {

			System.err.println(" ****--------- Security Applying ----------**** ");
		
		return http
				
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(req -> 
				 req.requestMatchers("/raj/v1/auth/*").permitAll()
				.requestMatchers("/management/**").hasAnyRole(Role.ADMIN.name(),Role.MEMBER.name())
				.requestMatchers("/administration/**").hasRole(Role.ADMIN.name())
				.requestMatchers(GET, "/management/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MEMBER_READ.name())
                .requestMatchers(POST, "/management/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MEMBER_CREATE.name())
				.anyRequest().authenticated())
				
				//this is for JWT configuration
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				
				.build();
		
		
	}
	
	@Bean
	public PasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(encoder());
		return provider;
		
	}

	@Bean
	public UserDetailsService userDetailsService() {

		
		return username -> userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
	}

	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
		
	}
}
