package com.sales.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sales.models.Product;

@Repository
public interface ProductInterface extends CrudRepository<Product, Long> {
	// CrudRepo = create/read/update/delete?
}
