package com.tec02.model.dto.impl;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.AbsIdAndCreateTimeDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RoleDto extends AbsIdAndCreateTimeDto {
	private String name;
	private String createBy;
}
