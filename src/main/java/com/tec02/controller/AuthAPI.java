package com.tec02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.AuthService;
import com.tec02.model.dto.AuthDto;
import com.tec02.model.dto.ResponseDto;

@RestController
@RequestMapping("api/v1/auth")
public class AuthAPI {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody AuthDto authDto){
		try {
			String jwt = this.authService.login(authDto);
			return ResponseDto.toResponse(true, jwt, "login success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
}
