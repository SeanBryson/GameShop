package com.cognixia.jump.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.NotEnoughStockException;
import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.repository.PurchaseRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetails;
import com.cognixia.jump.service.PurchaseService;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	@Autowired
	PurchaseService service;
	
	@Autowired
	PurchaseRepository repo;

	@Autowired
	JwtRequestFilter filter;
	
	@GetMapping()
	public List<Purchase> getPurchases() {
		return repo.findAll();
	}
	
	@PostMapping("/id")
	public ResponseEntity<?> purchaseGameById(@RequestParam(name="id") Long game_id,
			@RequestParam(name="user_id") Long user_id) 
		throws Exception {
	
		Purchase completed = service.purchaseGameId(game_id, user_id);
		
		return ResponseEntity.status(201).body(completed);
		
		
	}
	
	@PostMapping("/id-qty")
	public ResponseEntity<?> purchaseGameIdsAndQty(@RequestParam(name="game_id") Long game_id, 
			@RequestParam(name="user_id") Long user_id, @RequestParam(name="qty") int qty) 
		throws Exception {
		
		Purchase completed = service.purchaseGameIdAndQty(game_id, user_id, qty);
		
		return ResponseEntity.status(201).body(completed);
		
	}
	
	
	
}
