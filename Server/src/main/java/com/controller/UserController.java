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

import com.model.User;
import com.service.SongService;
import com.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	// GET all user
	@GetMapping("/getall")
	public List<User> getAll() {
		return (List<User>) userService.findAll();
	}

	// GET user by id
	@GetMapping("/{id}")
	public User getById(@PathVariable("id") int id) {
		System.out.println("Searching by username: " + id);
		User user = userService.findByID(id);
		if (user == null) {
			System.out.println("ID: " + id + " not found");
		}
		return user;
	}

	// POST user
		@PostMapping("/add")
		public User add(@RequestBody User user) {
			User _user = new User();
			_user.setUsername(user.getUsername());
			_user.setPassword(user.getPassword());
			_user.setName(user.getName());
			_user.setPhone(user.getPhone());
			_user.setEmail(user.getEmail());
			_user.setAddress(user.getAddress());
			_user.setAdmin(user.isAdmin());
			userService.save(_user);
			System.out.println("Successfully!");
			return _user;
		}
		

		// DELETE User by id
		@DeleteMapping("/delete/{id}")
		public void deleteByUsername(@PathVariable("id") int id) {
			User user = userService.findByID(id);
			if (user == null) {
				System.out.println("Not exsit ID! Don't delete!");
				return;
			}
			userService.delete(id);
			System.out.println("Deleted ID: " + id);
		}

		// PUT User to update
		@PutMapping("/update")
		public User update(@RequestBody User user) {
			User temp = userService.findByID(user.getIdUser());
			if (temp != null) {
				System.out.println("Update ID: " + user.getIdUser());
				userService.save(user);
				return user;
			}
			System.out.println("Not exsit username!");
			return temp;
		}

}
