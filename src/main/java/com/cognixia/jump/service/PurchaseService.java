package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.cognixia.jump.exception.NotEnoughStockException;
import com.cognixia.jump.exception.NotOldEnoughException;
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
	
	
	public Purchase purchaseGameIdAndQty(Long game_id, Long user_id, int qty) throws Exception {
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (user.isPresent() && game.isPresent()) {
			Game gameBuy = game.get();
			User userBuy = user.get();
			Date dob = userBuy.getDob();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, - 17);
			Date seventeen = cal.getTime();
			// if their DOB occurs before 17 years ago
			if (dob.compareTo(seventeen) < 0) {
				Double price = gameBuy.getPrice();
				Double total = qty * price;
				
				if (gameBuy.getQty() > qty) {
					
					gameBuy.setQty(gameBuy.getQty() - qty);
					gameRepo.save(gameBuy);
					Purchase newPurchase = new Purchase(null, userBuy, gameBuy, qty, total, new Date());
					return repo.save(newPurchase);			
				} throw new NotEnoughStockException(gameBuy.getName(), qty);
			
			} else {
				List<Game> approved = gameRepo.findWithRating();
				if(approved.isEmpty()) {
					throw new NotOldEnoughException("No age appropriate games available");
				} 
				
				if(approved.contains(gameBuy)) {
					Double price = gameBuy.getPrice();
					Double total = qty * price;
					
					if (gameBuy.getQty() > qty) {
						
						gameBuy.setQty(gameBuy.getQty() - qty);
						gameRepo.save(gameBuy);
						Purchase newPurchase = new Purchase(null, userBuy, gameBuy, qty, total, new Date());
						return repo.save(newPurchase);			
					} throw new NotEnoughStockException(gameBuy.getName(), qty);
				}
				throw new NotOldEnoughException("Not old enought to buy " + gameBuy.getName());
			}
				
		} throw new MethodArgumentNotValidException(null, null);
		
	}


	public Purchase purchaseGameId(Long game_id, Long user_id) 
		throws Exception {
		Optional<User> user = userRepo.findById(user_id);
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (user.isPresent() && game.isPresent()) {
			Game gameBuy = game.get();
			User userBuy = user.get();
			Double total = gameBuy.getPrice();
			
			if (gameBuy.getQty() > 0) {
				
				gameBuy.setQty(gameBuy.getQty()-1);
				gameRepo.save(gameBuy);
				Purchase newPurchase = new Purchase(null, userBuy, gameBuy, 1, total, new Date());
				return repo.save(newPurchase);			
			} throw new NotEnoughStockException(gameBuy.getName(), 1);
			
		} throw new MethodArgumentNotValidException(null, null);
		
	}

	public List<Purchase> findByGameId(Long game_id) throws ResourceNotFoundException {
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (game.isPresent()) {
			List<Purchase> purchases = repo.findAll();
			List<Purchase> found = new ArrayList<>();
			if(purchases.isEmpty()) {
				throw new ResourceNotFoundException("No purchases found");
			} 
			for (int i=0; i < purchases.size() ;i++) {
				if (purchases.get(i).getGame().getId() == game_id) {
					found.add(purchases.get(i));
				}
			}
		}
		
		return null;
	}


	public List<Purchase> findByUserId(Long user_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
