package com.cognixia.jump.controller;

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

import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetails;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	@Autowired 
	GameRepository gameRepo;
	
	@Autowired 
	UserRepository userRepo;
	
	@Autowired
	GameService gameServ;
	
	@Autowired
	UserService userServ;
	
	@Autowired
	JwtRequestFilter filter;
	
	
	@PostMapping("/game-by-id")
	public ResponseEntity<?> purchaseGameById(@RequestParam(name="id") Long game_id) 
		throws Exception {
		
		
//		filter.doFilter(request, response, filterChain);
//		
//				
//		Game purchased = gameServ.purchaseGameById(game_id);
//		
//		return ResponseEntity.status(200).body(purchased);
		return null;
		
	}
	
	@PostMapping("/game-by-id/user")
	public ResponseEntity<?> purchaseGameByIds(@RequestParam(name="id") Long game_id, @RequestParam(name="user_id") Long user_id) 
		throws Exception {
		
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (user.isPresent() && game.isPresent()) {
			Game gameAdd = game.get();
			
			User userAdd = user.get();
		}
		
		throw new MethodArgumentNotValidException(null, null);
		
//		filter.doFilter(request, response, filterChain);
//		
//				
//		Game purchased = gameServ.purchaseGameById(game_id);
//		
//		return ResponseEntity.status(200).body(purchased);
		
	}
}
