package com.learning.jwt.dto;

import com.learning.jwt.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RegisterRequest {
	
	private String firstname;

	private String lastname;

	private String email;

	private String password;

	private Role role;
}
