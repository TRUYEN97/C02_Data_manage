package com.tec02.model.dto;


import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AuthDto{
	private String userid;
	private String password;
}
