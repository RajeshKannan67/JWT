package com.learning.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administration")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	
	
	@GetMapping("/get")
	@PreAuthorize("hasAuthority('admin:read')")
	public String getAdmin() {return "Secured Endpoint :: ADMIN Controller ,Hi Admin this is a Static Page";}
	
	@PostMapping("/post")
	@PreAuthorize("hasAuthority('admin:create')")
	public String postAdmin() {return "Secured Endpoint :: ADMIN Controller ,Hi Admin You can able to create something in this page";}
}
