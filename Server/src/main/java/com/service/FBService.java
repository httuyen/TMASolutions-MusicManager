package com.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.FeedBackDTO;
import com.model.FeedBack;
import com.model.User;
import com.repository.FBRepository;

@Service
public class FBService {

	@Autowired
	FBRepository fBRepository;

	// Find all song
	public Iterable<FeedBack> findAll() {
		return fBRepository.findAll();
	}
	
	// Find FB by Id
	public FeedBack findById(int id) {
		Optional<FeedBack> op = fBRepository.findById(id);
		if (!op.isPresent()) {
			System.out.println("Not exist FB ID: " + id);
			return null;
		}
		return op.get();
	}

	// Add or update FB
	public void save(FeedBack temp) {
		fBRepository.save(temp);
	}

	// Delete FB
	public void delete(int id) {
		fBRepository.deleteById(id);
	}

	// Convert FeedBackDTO to Feedback
	public FeedBack convert(FeedBackDTO feedBackDTO) {
		FeedBack fb = new FeedBack();
		User user = new User();
		user.setIdUser(feedBackDTO.getIdUser());
		feedBackDTO.setUser(user);
		BeanUtils.copyProperties(feedBackDTO, fb);
		return fb;
	}
}
