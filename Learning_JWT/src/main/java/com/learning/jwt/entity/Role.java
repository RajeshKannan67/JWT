package com.learning.jwt.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

	ADMIN(Set.of(

			Permission.ADMIN_READ, 
			Permission.ADMIN_CREATE, 
			Permission.MEMBER_READ, 
			Permission.MEMBER_CREATE)

			),

	MEMBER(Set.of(Permission.MEMBER_READ, 
				  Permission.MEMBER_CREATE)

			);

	@Getter
	private final Set<Permission> permission;

	public List<SimpleGrantedAuthority> getAuthorities() {

		var authorities = getPermission().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getPermission())).collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

		return authorities;

	}

}
