package com.tec02.repository.impl;

import java.util.Optional;

import com.tec02.model.entity.impl.Product;
import com.tec02.repository.IBaseRepo;

public interface ProductRepo extends IBaseRepo<Product>{

	Optional<Product> findOneByName(String sProduct);

}
