package com.tec02.model.dto.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemDto extends HaveStartTimeAndFinishTimeDto{
	private String lower_limit;
	private String upper_limit;
	private String error_code;
	private String error_details;
	private String units;
	private String test_value;
	private String test_name;
	private String status;
}
