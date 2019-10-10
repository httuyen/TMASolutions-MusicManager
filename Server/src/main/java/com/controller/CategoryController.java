package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Category;
import com.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// GET all category
	@GetMapping("/getall")
	public List<Category> getAll() {
		return (List<Category>) categoryService.findAll();
	}

	// GET category by id
	@GetMapping("/{id}")
	public Category getById(@PathVariable("id") int id) {
		System.out.println("Searching by id: " + id);
		Category category = categoryService.findById(id);
		if (category == null) {
			System.out.println("ID: " + id + " not found");
		}
		return category;
	}

	// POST category
	@PostMapping("/add")
	public int add(@RequestBody Category category) {
		Category cate = new Category();
		cate.setTitle(category.getTitle());
		categoryService.save(cate);
		System.out.println("Successfully!");
		return cate.getIdCategory();
	}
	

	// DELETE category by id
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") int id) {
		Category category = categoryService.findById(id);
		if (category == null) {
			System.out.println("Not exsit ID! Don't delete!");
			return;
		}
		categoryService.delete(id);
		System.out.println("Deleted id: " + id);
	}

	// PUT category to update
	@PutMapping("/update")
	public Category update(@RequestBody Category category) {
		Category temp = categoryService.findById(category.getIdCategory());
		if (temp != null) {
			System.out.println("Update id: " + category.getIdCategory());
			categoryService.save(category);
			return category;
		}
		System.out.println("Not exsit ID!");
		return temp;
	}
}
