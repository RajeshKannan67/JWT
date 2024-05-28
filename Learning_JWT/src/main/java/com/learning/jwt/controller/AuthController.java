package com.learning.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.learning.jwt.auth.AuthenticationResponse;
import com.learning.jwt.dto.RegisterRequest;
import com.learning.jwt.entity.AuthenticationRequest;
import com.learning.jwt.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/raj/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
		System.out.println("---->"+registerRequest.getFirstname());
		System.out.println("---->"+registerRequest.getLastname());
		System.out.println("---->"+registerRequest.getEmail());
		System.out.println("---->"+registerRequest.getPassword());
		System.out.println("---->"+registerRequest.getRole());
		
		AuthenticationResponse authenticationResponse = authService.register(registerRequest);
		
		return ResponseEntity.ok(authenticationResponse);
		
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest registerRequest){
		System.out.println("---->"+registerRequest.getEmail());
		System.out.println("---->"+registerRequest.getPassword());
		
		AuthenticationResponse authenticationResponse = authService.authenticate(registerRequest);
		
		return ResponseEntity.ok(authenticationResponse);
		
		
	}
	
	@GetMapping("/no")
	public String no() {
		
		return "Filteration is No needed";
	}
	
	
}
