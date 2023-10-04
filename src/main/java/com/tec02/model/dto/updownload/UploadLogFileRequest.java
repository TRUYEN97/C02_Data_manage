package com.tec02.model.dto.updownload;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tec02.model.dto.RequestDto;
import com.tec02.util.Util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UploadLogFileRequest extends RequestDto{
	
	public Instant getStart_time() {
		return Util.stringCvtInstant(start_time, "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss");
	}

	public Instant getFinish_time() {
		return Util.stringCvtInstant(finish_time, "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss");
	}
	
	private List<ItemRequestDto> tests = new ArrayList<>();
	
}
