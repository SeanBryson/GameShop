package com.cognixia.jump.controller;

import java.util.Collections;
import java.util.Comparator;
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
import com.cognixia.jump.model.NameCompare;
import com.cognixia.jump.model.QtyCompare;
import com.cognixia.jump.model.EsrbCompare;
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
		List<Game> games = repo.findAll();
		Collections.sort(games);
		//games.sort(null);
		return games;
	}
	
	@GetMapping("/sortqty")
	public List<Game> getGamesSortQty() {
		List<Game> games = repo.findAll();
		QtyCompare qtyCompare = new QtyCompare();
        Collections.sort(games, qtyCompare);
		return games;
	}
	
	@GetMapping("/sortname")
	public List<Game> getGamesSortName() {
		List<Game> games = repo.findAll();
		NameCompare nameCompare = new NameCompare();
        Collections.sort(games, nameCompare);
		return games;
	}
	
	@GetMapping("/sortesrb")
	public List<Game> getGamesSortEsrb() {
		List<Game> games = repo.findAll();
		EsrbCompare esrbCompare = new EsrbCompare();
        Collections.sort(games, esrbCompare);
		return games;
	}
	
	@GetMapping("/sort")
	public List<Game> getGamesSort() {
		List<Game> games = repo.findAll();
		// this method will not use our custom functionality for Esrb
		// really only helpful for near duplicates
		Collections.sort(games, Comparator.comparing(Game::getName)
	            .thenComparing(Game::getEsrb)
	            .thenComparing(Game::getQty));
		return games;
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
	public ResponseEntity<?> deleteGameById(@RequestParam(name="game_id") Long id) 
			throws Exception {
		
		Game deleted = service.deleteGameById(id);
		
		return ResponseEntity.status(200).body(deleted);
		
	}
		
}
