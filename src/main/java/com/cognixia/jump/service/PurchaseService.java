package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	
	public Purchase purchaseGameIdAndQty(Long game_id, int qty) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Optional<User> user = userRepo.findByUsername(username);
		Optional<Game> game = gameRepo.findById(game_id);
		
		if (user.isPresent() && game.isPresent()) {
			Game gameBuy = game.get();
			User userBuy = user.get();
			Date dob = userBuy.getDob();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, - 17);
			Date seventeen = cal.getTime();
			System.out.println(dob.toString());
			System.out.println(seventeen.toString());
			// if their DOB occurs before 17 years ago
			if (dob.compareTo(seventeen) < 0) {
				Double price = gameBuy.getPrice();
				Double total = qty * price;
				
				if (gameBuy.getQty() > qty) {
					Purchase newPurchase = new Purchase(null, userBuy, gameBuy, qty, total, new Date());
					gameBuy.setQty(gameBuy.getQty() - qty);
					gameRepo.save(gameBuy);
					repo.save(newPurchase);
					return newPurchase;	
				} throw new NotEnoughStockException(gameBuy.getName(), qty);
			
			} else {
				List<Game> approved = gameRepo.findWithRating();
				if(approved.isEmpty()) {
					throw new NotOldEnoughException("No age appropriate games available");
				} 
				
				// If the age appropriate game is available
				if(approved.contains(gameBuy)) {
					Double price = gameBuy.getPrice();
					Double total = qty * price;
					
					if (gameBuy.getQty() > qty) {
						Purchase newPurchase = new Purchase(null, userBuy, gameBuy, qty, total, new Date());
						gameBuy.setQty(gameBuy.getQty() - qty);
						gameRepo.save(gameBuy);
						repo.save(newPurchase);		
						return newPurchase;
					} throw new NotEnoughStockException(gameBuy.getName(), qty);
				}
				throw new NotOldEnoughException("Not old enough to buy " + gameBuy.getName());
			}	
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
			return found;
		} 
		throw new ResourceNotFoundException("game", game_id.toString());
	}


	public List<Purchase> findByUser() throws ResourceNotFoundException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isPresent()) {
			Long user_id = user.get().getId();
			List<Purchase> purchases = repo.findAll();
			List<Purchase> found = new ArrayList<>();
			if(purchases.isEmpty()) {
				throw new ResourceNotFoundException("No purchases found");
			} 
			for (int i=0; i < purchases.size() ;i++) {
				if (purchases.get(i).getUser().getId() == user_id) {
					found.add(purchases.get(i));
				}
			}
			return found;
		} 
		throw new ResourceNotFoundException("User with username" + username + " not found");
	}

	public Purchase deleteById(Long id) throws ResourceNotFoundException {
		Optional<Purchase> selected = repo.findById(id);
		
		if (selected.isPresent()) {
			
			Purchase toDelete = selected.get();
			
			repo.deleteById(toDelete.getId());
			
			return toDelete;
		} else {
			throw new ResourceNotFoundException("Purchase with id " + id.toString() + " not Found");
		}
	}


	public Purchase updatePurchase(Purchase pur) throws ResourceNotFoundException, NotEnoughStockException {
		Optional<Purchase> selected = repo.findById(pur.getId());
		
		if (selected.isPresent()) {
			Purchase existingPurchase = selected.get();
			int currentQty = existingPurchase.getQty();
			Optional<Game> game = gameRepo.findById(existingPurchase.getGame().getId());
			if (game.isPresent()) {
				int stock = game.get().getQty();
				double price = game.get().getPrice();
				// only want the user to edit qty
				// make sure there is enough stock
				if (pur.getQty() <= (currentQty+stock) ) {
					existingPurchase.setQty(pur.getQty());
					// adjust total based on qty
					existingPurchase.setTotal(price * pur.getQty());
					existingPurchase.setTime(new Date());
					game.get().setQty(stock+currentQty-pur.getQty());
					gameRepo.save(game.get());
					return repo.save(existingPurchase);
				} else {
					throw new NotEnoughStockException(game.get().getName(), game.get().getQty());
				}
			} else {
				throw new ResourceNotFoundException("Game", existingPurchase.getGame().getId().toString());
			}
		} else {
			throw new ResourceNotFoundException("Purchase not found");
		}
	}
}
