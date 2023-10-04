package com.tec02.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.tec02.Service.impl.impl.LocationService;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.LineDto;
import com.tec02.model.dto.impl.ProductDto;
import com.tec02.model.dto.impl.StationDto;

@RestController
@RequestMapping("api/v1/location")
public class LocationAPI {
	@Autowired
	private LocationService locationService;

	@GetMapping
	public ResponseEntity<ResponseDto> find() {
		try {
			List<String> pname = new ArrayList<>();
			List<String> sname = new ArrayList<>();
			List<String> lname = new ArrayList<>();
			List<ProductDto> products = locationService.getAllProductDto();
			if (products != null) {
				for (ProductDto product : products) {
					pname.add(product.getName());
				}
			}
			List<StationDto> stations = locationService.getAllStationDto();
			if (stations != null) {
				for (StationDto station : stations) {
					sname.add(station.getName());
				}
			}
			List<LineDto> lines = locationService.getAllLineDto();
			if (lines != null) {
				for (LineDto line : lines) {
					lname.add(line.getName());
				}
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("product", pname);
			jsonObject.put("station", sname);
			jsonObject.put("line", lname);
			return ResponseDto.toResponse(true, jsonObject, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
}
