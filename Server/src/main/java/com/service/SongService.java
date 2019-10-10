package com.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.SongDTO;
import com.model.Category;
import com.model.Song;
import com.model.User;
import com.repository.SongRepository;

@Service
public class SongService {

	@Autowired
	SongRepository songRepository;

	// Find all song
	public Iterable<Song> findAll() {
		return songRepository.findAll();
	}
	
	// Find song by Id
	public Song findById(int id) {
		Optional<Song> op = songRepository.findById(id);
		if (!op.isPresent()) {
			System.out.println("Not exist songID: " + id);
			return null;
		}
		return op.get();
	}

	// Add or update song
	public void save(Song temp) {
		songRepository.save(temp);
	}

	// Delete song
	public void delete(int id) {
		songRepository.deleteById(id);
	}

	// Convert SongDTO to Song
	public Song convert(SongDTO songDTO) {
		Song song = new Song();
		Category category = new Category();
		category.setIdCategory(songDTO.getIdCategory());
		User user = new User();
		user.setIdUser(songDTO.getIdUser());
		songDTO.setUser(user);
		songDTO.setCategory(category);
		BeanUtils.copyProperties(songDTO, song);
		return song;
	}
}
