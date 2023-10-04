package com.tec02.Service.impl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.model.dto.impl.LineDto;
import com.tec02.model.dto.impl.ProductDto;
import com.tec02.model.dto.impl.StationDto;
import com.tec02.model.entity.impl.Line;
import com.tec02.model.entity.impl.Product;
import com.tec02.model.entity.impl.Station;
import com.tec02.repository.impl.LineRepo;
import com.tec02.repository.impl.ProductRepo;
import com.tec02.repository.impl.StationRepo;
import com.tec02.util.ModelMapperUtil;

@Service
public class LocationService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private StationRepo stationRepo;
	@Autowired
	private LineRepo lineRepo;

	public Product getProduct(String name) {
		Product entity = this.productRepo.findOneByName(name).orElse(null);
		if(entity == null) {
			entity = new Product();
			entity.setName(name);
			return this.productRepo.save(entity);
		}
		return entity;
	}

	public Station getStation(String name) {
		Station entity = this.stationRepo.findOneByName(name).orElse(null);
		if(entity == null) {
			entity = new Station();
			entity.setName(name);
			return this.stationRepo.save(entity);
		}
		return entity;
	}

	public Line getLine(String name) {
		Line entity = this.lineRepo.findOneByName(name).orElse(null);
		if(entity == null) {
			entity = new Line();
			entity.setName(name);
			return this.lineRepo.save(entity);
		}
		return entity;
	}

	public List<Product> getAllProduct() {
		return this.productRepo.findAll();
	}

	public List<Station> getAllStation() {
		return this.stationRepo.findAll();
	}

	public List<Line> getAllLine() {
		return this.lineRepo.findAll();
	}

	public List<ProductDto> getAllProductDto() {
		List<Product> products = getAllProduct();
		return ModelMapperUtil.mapAll(products, ProductDto.class);
	}

	public List<StationDto> getAllStationDto() {
		List<Station> stations = getAllStation();
		return ModelMapperUtil.mapAll(stations, StationDto.class);
	}

	public List<LineDto> getAllLineDto() {
		List<Line> lines = getAllLine();
		return ModelMapperUtil.mapAll(lines, LineDto.class);
	}

}
