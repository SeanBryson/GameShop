package com.cognixia.jump.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public User updateUser(User user) throws UsernameNotFoundException {
		
		Optional<User> curUser = repo.findById(user.getId());
		
		if (curUser.isPresent()) {
			return repo.save(user);
		} else {
			throw new UsernameNotFoundException(user.getUsername());
		}
	
	}
		
}
