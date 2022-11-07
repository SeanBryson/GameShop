package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Game;
import com.cognixia.jump.model.Purchase;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.PurchaseRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.PurchaseService;
import com.cognixia.jump.util.JwtUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {
	
	@MockBean
	PurchaseService service;
	
	@MockBean
	PurchaseRepository repo;
	
	@MockBean
	MyUserDetailsService muds;
	
	@MockBean
	JwtUtil jwtu;
	
	@InjectMocks
	PurchaseController controller;
	
	@Autowired
	MockMvc mockMvc;
	
	@WithMockUser(username="admin")
	@Test
	void testGetPurchases() throws Exception {
		
		String uri = "/api/purchase";
		
		List<Purchase> purchases = new ArrayList<Purchase>();
		
		purchases.add( new Purchase(1L, new User(), new Game(), 1, 59.99, new Date()));
		purchases.add( new Purchase(2L, new User(), new Game(), 2, 69.99, new Date()));
		
		when( repo.findAll() ).thenReturn( purchases );
		
		mockMvc.perform( get(uri) )
		.andDo( print() )
		.andExpect( status().isOk() )
		.andExpect( jsonPath("$.length()").value( purchases.size() ) ) // check size of resulting array
		.andExpect( jsonPath("$[0].id").value( purchases.get(0).getId() ) ) // checks if first student has id = 1
		.andExpect( jsonPath("$[1].user.id").value( purchases.get(1).getUser().getId() ) ) // checks second student has lastName = platypus
		.andExpect( jsonPath("$[0].game.id").value( purchases.get(0).getGame().getId() ) )
		;

		// verify can check how many times a method is called during a test
		verify( repo, times(1) ).findAll(); // check this method is only called once
		
		// make sure no more methods from repo are being called
		verifyNoMoreInteractions( repo );
	}

//	@Test
//	void testPurchaseGameById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPurchaseGameIdsAndQty() {
//		fail("Not yet implemented");
//	}

}
