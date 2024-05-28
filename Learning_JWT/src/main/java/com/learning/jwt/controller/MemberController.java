package com.learning.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class MemberController {

	@GetMapping("/get")
	public String getMember()  { return "Secured Endpoint GET  :: This is a MEMBER COntroller "; }
	
	@PostMapping("/post")
	public String postMember() { return "Secured Endpoint POST :: This is a MEMBER COntroller "; }

}
