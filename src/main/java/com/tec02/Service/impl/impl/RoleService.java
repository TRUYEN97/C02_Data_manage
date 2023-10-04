package com.tec02.Service.impl.impl;

import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.impl.RoleDto;
import com.tec02.model.entity.impl.Role;
import com.tec02.repository.impl.RoleRepository;

@Service
public class RoleService extends BaseService<RoleDto, Role> {
	protected RoleService(RoleRepository repository) {
		super(repository, RoleDto.class, Role.class);
	}
	
}
