package com.tec02.model.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.tec02.model.entity.HaveName;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product extends HaveName{

	@OneToMany(mappedBy = "product")
	private List<LogDetail> logDetails = new ArrayList<>();
}
