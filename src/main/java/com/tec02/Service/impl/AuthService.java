package com.tec02.Service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.impl.UserService;
import com.tec02.model.dto.AuthDto;
import com.tec02.model.dto.impl.UserDto;
import com.tec02.model.entity.impl.Role;
import com.tec02.model.entity.impl.User;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserService userService;
	private final JwtServeice jwtServeice;
	private final AuthenticationManager authenticationManager;

	@Transactional
	public String login(AuthDto authDto) throws Exception {
		String userid = authDto.getUserid();
		String password = authDto.getPassword();
		if (Util.isNullorEmpty(userid)) {
			throw new Exception("userid must not be null or empty!");
		}
		if (Util.isNullorEmpty(password)) {
			throw new Exception("password must not be null or empty!");
		}
		User user = this.userService.findByUserIdAndUserstatus(userid, true);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), password));
		Set<Role> roles = user.getUserRoles();
		UserDto userDto = ModelMapperUtil.map(user, UserDto.class);
		String jwt;
		if (roles == null || roles.isEmpty()) {
			jwt = this.jwtServeice.generateToken(Map.of(), userDto);
		} else {
			jwt = this.jwtServeice.generateToken(Map.of("role", roles.iterator().next().getAuthority()), userDto);
		}
		return String.format("Bearer %s", jwt);
	}
}
