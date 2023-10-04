package com.tec02.model.dto;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
	
	public ResponseDto(boolean result, Object data, String message, Object... param) {
		super();
		this.result = result;
		this.message = String.format(message, param);
		this.data = data;
	}
	private boolean result;
	private String message;
	private Object data;
	
	public static ResponseEntity<ResponseDto> toResponse(boolean status, Object data, String message, Object... param) {
		return ResponseEntity.ok().body(new ResponseDto(status, data, message, param));
		
	}

	public static ResponseEntity<Resource> toDownloadResponse(HttpHeaders headers, Resource resource) throws IOException {
		return ResponseEntity.ok()
		.headers(headers)
		.contentType(MediaType.APPLICATION_OCTET_STREAM)
		.contentLength(resource.getFile().length())
		.body(resource);
	}
}
