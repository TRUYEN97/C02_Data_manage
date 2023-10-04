package com.tec02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.RoleService;
import com.tec02.Service.impl.impl.UserService;
import com.tec02.model.dto.AccountDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.UserDto;
import com.tec02.model.entity.impl.Role;
import com.tec02.model.entity.impl.User;

@RestController
@RequestMapping("api/v1/user")
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleServer;

	@PostMapping
	public ResponseEntity<ResponseDto> addUserWithRole(
			@RequestParam(value = "roleid", required = false) List<Long> roleIds, @RequestBody AccountDto accountDto) {
		try {
			List<Role> roles = this.roleServer.findAllByIds(roleIds);
			UserDto userSaved = this.userService.addUserWithRoles(roles, accountDto);
			return ResponseDto.toResponse(true, userSaved, "Save user success!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			UserDto userDto = userService.updateDto(id, user);
			if (userDto == null) {
				return ResponseDto.toResponse(false, null, "update user failed");
			}
			return ResponseDto.toResponse(true, userDto, "update user success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}/roleid")
	public ResponseEntity<ResponseDto> updateUserWithRole(@RequestParam("ids") List<Long> roleIds,
			@PathVariable("id") Long userID) {
		try {
			List<Role> roles = this.roleServer.findAllByIds(roleIds);
			User user = this.userService.findOne(userID);
			UserDto userSaved = this.userService.addUserWithRoles(roles, user);
			return ResponseDto.toResponse(true, userSaved, "Save user success!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDto> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.delete(id);
			return ResponseDto.toResponse(true, id, "delete user ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

}
