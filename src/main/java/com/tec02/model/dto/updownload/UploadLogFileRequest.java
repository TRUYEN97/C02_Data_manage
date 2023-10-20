package com.tec02.model.dto.updownload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.RequestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UploadLogFileRequest extends RequestDto {

	private List<ItemRequestDto> tests = new ArrayList<>();

}
