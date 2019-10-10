package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
