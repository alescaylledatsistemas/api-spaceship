package com.w2m.tvmedia.auth.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.tvmedia.auth.service.AuthService;
import com.w2m.tvmedia.auth.web.dto.JwtAuthDTO;
import com.w2m.tvmedia.auth.web.dto.LoginDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping()
	public ResponseEntity<JwtAuthDTO> login(@RequestBody LoginDTO loginDto) {
		String token = authService.login(loginDto);

		JwtAuthDTO jwtAuthResponse = new JwtAuthDTO();
		jwtAuthResponse.setAccessToken(token);

		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}

}
