package com.learning.jwt.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.jwt.auth.AuthenticationResponse;
import com.learning.jwt.dto.RegisterRequest;
import com.learning.jwt.entity.AuthenticationRequest;
import com.learning.jwt.entity.User;
import com.learning.jwt.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        System.out.println("*****Generating JWT Token*****");
        var user = User.builder()
            .firstname(registerRequest.getFirstname())
            .lastname(registerRequest.getLastname())
            .email(registerRequest.getEmail())
            .password(encoder.encode(registerRequest.getPassword()))
            .role(registerRequest.getRole())
            .build();
        
        var savedUser = userRepo.save(user);
        String jwt = jwtService.generateToken(savedUser);

        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .build();
    }

	public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						registerRequest.getEmail(),registerRequest.getPassword()
						
						) );
		
		
		var user = userRepo.findByEmail(registerRequest.getEmail()).orElseThrow();
		
		 String jwt = jwtService.generateToken(user);

	        return AuthenticationResponse.builder()
	                .accessToken(jwt)
	                .build();	
	        }
}
