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

import com.dto.SongDTO;
import com.model.Category;
import com.model.Song;
import com.model.User;
import com.service.CategoryService;
import com.service.SongService;
import com.service.UserService;

@RestController
@RequestMapping("/song")
public class SongController {

	@Autowired
	SongService songService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	UserService userService;
	
	// GET all song
	@GetMapping("/getall")
	public List<Song> getAll() {
		return (List<Song>) songService.findAll();
	}

	// GET song by id
	@GetMapping("/{id}")
	public Song getById(@PathVariable("id") int id) {
		System.out.println("Searching by id: " + id);
		Song song = songService.findById(id);
		if (song == null) {
			System.out.println("ID song: " + id + " not found");
		}
		return song;
	}

	// POST song
	@SuppressWarnings("null")
	@PostMapping("/add")
	public int add(@RequestBody SongDTO songDTO) {
		Category category = new Category();
		category = categoryService.findById(songDTO.getIdCategory());
		User user = new User();
		user = userService.findByID(songDTO.getIdUser());
		if (category == null) {
			return (Integer) null;
		}else if(user == null) {
			return (Integer) null;
		}else {
			Song song = new Song();
			song.setName(songDTO.getName());
			song.setMusican(songDTO.getMusican());
			song.setSinger(songDTO.getSinger());
			song.setDateCreate(songDTO.getDateCreate());
			song.setLink(songDTO.getLink());
			song.setCategory(category);
			song.setUser(user);
			songService.save(song);
			System.out.println("Successfully!");
			return song.getId();
		}
	}
	
	


	// DELETE Song by id
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") int id) {
		Song song = songService.findById(id);
		if (song == null) {
			System.out.println("Not exsit ID! Don't delete!");
			return;
		}
		songService.delete(id);
		System.out.println("Deleted id: " + id);
	}

	// PUT Song to update
	@PutMapping("/update")
	public Song update(@RequestBody SongDTO songDTO) {
		Song temp = songService.findById(songDTO.getId());
		if (temp != null) {
			System.out.println("Update id: " + songDTO.getId());
			//so important ^^
			temp = songService.convert(songDTO);
			songService.save(temp);
			return temp;
		}
		System.out.println("Not exsit ID!");
		return temp;
	}

}
