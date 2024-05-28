package com.learning.jwt.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	 JwtService jwtService;
	@Autowired
	@Lazy
	 UserDetailsService detailsService;
	
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		//System.out.println("Authorization "+authHeader);
		String jwt;
		String email;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			
			System.out.println("Request Without Bearer......!");
			
			filterChain.doFilter(request, response);
			
			return;
		}
		
		System.out.println(authHeader);
		//Extract jwt from the Authorization
		jwt = authHeader.substring(6);
		//verify whether user is present in DB
		//verify whether token is valid
		email = jwtService.extractUsername(jwt); // extracting the UserName or e-mail from the Encrypted JWT Token
		System.out.println("Extracted Value "+ email);
		
		//if the user is present and no authentication Object in securityContext
		
		if(email != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			
			UserDetails userDetails = this.detailsService.loadUserByUsername(email);
			
			
			UsernamePasswordAuthenticationToken authToken = new
					
					UsernamePasswordAuthenticationToken (userDetails,null ,userDetails.getAuthorities());
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);;
		}
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/raj/v1/auth");
	}

}
