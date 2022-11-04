package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;

@Service
public class GameService {

	
	@Autowired
	GameRepository repo;
		
		
	public Game updateStock(Game game) throws ResourceNotFoundException {
		
		Optional<Game> selected = repo.findById(game.getId());
		
		if (selected.isPresent()) {
			Game existingGame = selected.get();
			
			existingGame.setName(game.getName());
			existingGame.setEsrb(game.getEsrb());
			existingGame.setPrice(game.getPrice());
			existingGame.setQty(game.getQty());
			existingGame.setUpdated(game.getUpdated());
			existingGame.setUsers(game.getUsers());
			
			return repo.save(existingGame);
		} else {
			throw new ResourceNotFoundException(game.getName());
		}
	}


	public Game deleteGame(Game game) throws ResourceNotFoundException {
		Optional<Game> selected = repo.findById(game.getId());
		
		if (selected.isPresent()) {
			
			Game toDelete = selected.get();
			
			repo.deleteById(toDelete.getId());
			
			return toDelete;
		} else {
			throw new ResourceNotFoundException("Game with id " + selected.get().getId().toString() + " not Found");
		}
	}


	public Game deleteGameById(Long id) throws ResourceNotFoundException {
	Optional<Game> selected = repo.findById(id);
		
		if (selected.isPresent()) {
			
			Game toDelete = selected.get();
			
			repo.deleteById(toDelete.getId());
			
			return toDelete;
		} else {
			throw new ResourceNotFoundException("Game with id " + id.toString() + " not Found");
		}
	}

}
