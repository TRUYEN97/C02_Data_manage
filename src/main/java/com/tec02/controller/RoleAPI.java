package com.tec02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.RoleService;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.RoleDto;
import com.tec02.model.entity.impl.Role;

@RestController
@RequestMapping("api/v1/role")
public class RoleAPI{

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestBody RoleDto role) {
		try {
			RoleDto roleDto = this.roleService.update(null, role);
			if (roleDto == null) {
				return ResponseDto.toResponse(false, null, "create role failed!");
			}
			return ResponseDto.toResponse(true, roleDto, "create role ok!");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> update(@RequestBody Role role, @PathVariable("id") Long id) {
		try {
			RoleDto roleDto = this.roleService.updateDto(id, role);
			if (roleDto == null) {
				return ResponseDto.toResponse(false, null, "update role failed!");
			}
			return ResponseDto.toResponse(true, roleDto, "update role ok!");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
}
