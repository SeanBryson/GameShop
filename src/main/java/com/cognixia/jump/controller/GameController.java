package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.service.GameService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	GameRepository repo;
	
	@Autowired
	GameService service;
	
	@Operation(summary = "Get all the games in the current inventory", 
			   description = "Gets all the games from the game table in the spring_db database. "
			   		+ "Each game grabbed has an id, name, esrb rating, price, date it was "
			   		+ "last updated, and list previous purchases")
	@GetMapping()
	public List<Game> getGames() {
		return repo.findAll();
	}
	
	@PostMapping()
	public ResponseEntity<?> addGame(@RequestBody Game game) {
		
		game.setId(null);
		
		Game added = repo.save(game);
		
		return ResponseEntity.status(201).body(added);
	}
	
	@PutMapping()
	public ResponseEntity<?> updateStock(@RequestBody Game game) throws Exception {
		
		Game updated = service.updateStock(game);
		
		return ResponseEntity.status(200).body(updated);
		
	}
	

	@DeleteMapping()
	public ResponseEntity<?> deleteGameById(@RequestParam(name="id") Long id) 
			throws Exception {
		
		Game deleted = service.deleteGameById(id);
		
		return ResponseEntity.status(200).body(deleted);
		
	}
		
}
