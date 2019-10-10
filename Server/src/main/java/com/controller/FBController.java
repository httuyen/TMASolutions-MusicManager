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

import com.dto.FeedBackDTO;
import com.model.FeedBack;
import com.model.User;
import com.service.FBService;
import com.service.UserService;

@RestController
@RequestMapping("/feedback")
public class FBController {
	@Autowired
	UserService userService;
	
	@Autowired
	FBService fBService;
	
	// GET all fb
	@GetMapping("/getall")
	public List<FeedBack> getAll() {
		return (List<FeedBack>) fBService.findAll();
	}

	// GET fb by id
	@GetMapping("/{id}")
	public FeedBack getById(@PathVariable("id") int id) {
		System.out.println("Searching by id: " + id);
		FeedBack fb = fBService.findById(id);
		if (fb == null) {
			System.out.println("ID FB: " + id + " not found");
		}
		return fb;
	}

	// POST fb
	@PostMapping("/add")
	public FeedBack add(@RequestBody FeedBackDTO fBDTO) {
		User user = new User();
		user = userService.findByID(fBDTO.getIdUser());
		if(user == null) {
			return null;
		}else {
			FeedBack fb = new FeedBack();
			fb.setContent(fBDTO.getContent());
			fb.setDateCreate(fBDTO.getDateCreate());
			fb.setUser(user);
			fBService.save(fb);
			System.out.println("Successfully!");
			return fb;
		}
	}
	
	// DELETE fb by id
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") int id) {
		FeedBack fb = fBService.findById(id);
		if (fb == null) {
			System.out.println("Not exsit ID! Don't delete!");
			return;
		}
		fBService.delete(id);
		System.out.println("Deleted id: " + id);
	}

	// PUT fb to update
	@PutMapping("/update")
	public FeedBack update(@RequestBody FeedBackDTO fBDTO) {
		FeedBack temp = fBService.findById(fBDTO.getId());
		if (temp != null) {
			System.out.println("Update id: " + fBDTO.getId());
			temp = fBService.convert(fBDTO);
			fBService.save(temp);
			return temp;
		}
		System.out.println("Not exsit ID!");
		return temp;
	}

}
