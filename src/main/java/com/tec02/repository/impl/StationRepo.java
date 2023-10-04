package com.tec02.repository.impl;

import java.util.Optional;

import com.tec02.model.entity.impl.Station;
import com.tec02.repository.IBaseRepo;

public interface StationRepo extends IBaseRepo<Station> {

	Optional<Station> findOneByName(String name);
}
