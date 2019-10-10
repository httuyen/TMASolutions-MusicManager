package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// Find all User
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	// Find User by username
	public User findByID(int id) {
		Optional<User> op = userRepository.findById(id);
		if (!op.isPresent()) {
			System.out.println("Not exist user id: " + id);
			return null;
		}
		return op.get();
	}

	// Add or update User
	public void save(User user) {
		userRepository.save(user);
	}

	// Delete User
	public void delete(int id) {
		userRepository.deleteById(id);
	}
}
