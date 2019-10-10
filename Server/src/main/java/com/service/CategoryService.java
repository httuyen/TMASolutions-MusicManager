package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Category;
import com.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	// Find all category
	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}

	// Find category by Id
	public Category findById(int id) {
		Optional<Category> op = categoryRepository.findById(id);
		if (!op.isPresent()) {
			System.out.println("Not exist catID: " + id);
			return null;
		}
		return op.get();
	}

	// Add or update category
	public void save(Category category) {
		categoryRepository.save(category);
	}

	// Delete category
	public void delete(int id) {
		categoryRepository.deleteById(id);
	}
}
