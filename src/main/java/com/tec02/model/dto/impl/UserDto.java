package com.tec02.model.dto.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserDto extends AbsIdAndCreateTimeDto {
	private String userId;
	private String name;
	private boolean userstatus;
	private Set<String> userRoles = new HashSet<>();
	protected String createBy;
}
