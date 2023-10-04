package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.AccountDto;
import com.tec02.model.dto.impl.UserDto;
import com.tec02.model.entity.impl.Role;
import com.tec02.model.entity.impl.User;
import com.tec02.repository.impl.UserRepository;
import com.tec02.util.ModelMapperUtil;
import com.tec02.util.Util;

@Service
public class UserService extends BaseService<UserDto, User> implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	protected UserService(UserRepository userRepository) {
		super(userRepository, UserDto.class, User.class);
	}

	@Override
	public UserDto updateDto(Long id, User entity) {
		String password;
		if (entity != null && (password = entity.getPassword()) != null) {
			entity.setPassword(passwordEncoder.encode(password));
		}
		return super.updateDto(id, entity);
	}

	public UserDto addUserWithRoles(List<Role> roles, AccountDto accountDto) throws Exception {
		if (Util.isNullorEmpty(accountDto.getName())) {
			throw new Exception("name must not be null or empty!");
		}
		if (Util.isNullorEmpty(accountDto.getUserId())) {
			throw new Exception("userId must not be null or empty!!");
		}
		if (Util.isNullorEmpty(accountDto.getPassword())) {
			throw new Exception("password must not be null or empty!!");
		}
		if (this.existsByUserIdOrName(accountDto.getUserId(), accountDto.getName())) {
			throw new Exception("User has exists!");
		}
		User user = ModelMapperUtil.map(accountDto, User.class);
		return addUserWithRoles(roles, user);
	}

	public UserDto addUserWithRoles(List<Role> roles, User user) throws Exception {
		if (!Util.isNullorEmpty(roles)) {
			user.addRoles(roles);
		}
		return updateDto(user.getId(), user);
	}

	public List<UserDto> findUser(List<Long> ids, boolean status) {
		List<User> users = this.userRepository.findAllById(ids);
		return ModelMapperUtil.mapAll(users, UserDto.class);
	}

	public boolean existsByUserIdOrName(String userId, String username) {
		return this.userRepository.existsByUserIdOrName(userId, username);
	}

	public List<UserDto> findAllByStatus(boolean status) {
		List<User> users = this.userRepository.findAllByUserstatus(status);
		return ModelMapperUtil.mapAll(users, UserDto.class);
	}

	public User findByUserIdAndUserstatus(String userid, boolean status) {
		return this.userRepository.findByUserIdAndUserstatus(userid, status)
				.orElseThrow(() -> new RuntimeException("Incorrect username or password"));
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findOneByName(username).orElse(null);
	}

}
