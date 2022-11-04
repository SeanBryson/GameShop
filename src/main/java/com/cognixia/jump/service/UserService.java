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
		
		Optional<User> curUser = repo.findByUsername(user.getUsername());
		
		if (curUser.isPresent()) {
			User existingUser = curUser.get();
			
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setEmail(user.getEmail());
			existingUser.setDob(user.getDob());
			existingUser.setPhone(user.getPhone());
			existingUser.setRole(user.getRole());
			existingUser.setEnabled(user.isEnabled());
			existingUser.setGames(user.getGames());
			
			return repo.save(existingUser);
		} else {
			throw new UsernameNotFoundException(user.getUsername());
		}
	
	}
		
}
