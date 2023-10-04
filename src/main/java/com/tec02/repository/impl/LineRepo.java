package com.tec02.repository.impl;

import java.util.Optional;

import com.tec02.model.entity.impl.Line;
import com.tec02.repository.IBaseRepo;

public interface LineRepo extends IBaseRepo<Line> {

	Optional<Line> findOneByName(String name);
}
