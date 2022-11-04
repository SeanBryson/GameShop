package com.cognixia.jump.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cognixia.jump.exception.NotEnoughStockException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.repository.PurchaseRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired 
	GameRepository gameRepo;
	
	
	public Purchase purchaseGameIdsAndQty(Long game_id, Long user_id, int qty) throws Exception {
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (user.isPresent() && game.isPresent()) {
			Game gameBuy = game.get();
			User userBuy = user.get();
			Double price = gameBuy.getPrice();
			Double total = qty * price;
			
			if (gameBuy.getQty() > qty) {
				
				gameBuy.setQty(gameBuy.getQty() - qty);
				gameRepo.save(gameBuy);
				Purchase newPurchase = new Purchase(null, userBuy, gameBuy, qty, total, new Date());
				return repo.save(newPurchase);			
			} throw new NotEnoughStockException(gameBuy.getName(), qty);
			
		} throw new MethodArgumentNotValidException(null, null);
		
	}
	
}
