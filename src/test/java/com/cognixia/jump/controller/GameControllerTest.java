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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Game;
import com.cognixia.jump.repository.GameRepository;
import com.cognixia.jump.service.GameService;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
class GameControllerTest {

	@MockBean
	GameService service;
	
	@MockBean
	GameRepository repo;
	
	@MockBean
	MyUserDetailsService muds;
	
	@MockBean
	JwtUtil jwtu;
	
	@MockBean
	PasswordEncoder pe;
	
	@InjectMocks
	GameController controller;
	
	@Autowired
	MockMvc mockMvc;

	@WithMockUser(username="admin")
	@Test
	void testGetGames() throws Exception {
		String uri = "/api/game";
		
		List<Game> games = new ArrayList<Game>();
		
		games.add( new Game(1L, "game1", "RP", 49.99, 7, new Date(), null));
		games.add( new Game(2L, "game2", "M", 59.99, 6, new Date(), null));
		
		
		when( repo.findAll() ).thenReturn( games );
		
		mockMvc.perform( get(uri) )
		.andDo( print() )
		.andExpect( status().isOk() )
		.andExpect( jsonPath("$.length()").value( games.size() ) ) // check size of resulting array
		.andExpect( jsonPath("$[0].id").value( games.get(0).getId() ) ) // checks if first student has id = 1
		.andExpect( jsonPath("$[1].price").value( games.get(1).getPrice() ) ) // checks second student has lastName = platypus
		.andExpect( jsonPath("$[0].esrb").value( games.get(0).getEsrb()) )
		;

		// verify can check how many times a method is called during a test
		verify( repo, times(1) ).findAll(); // check this method is only called once
		
		// make sure no more methods from repo are being called
		verifyNoMoreInteractions( repo );
	}
//
//	@Test
//
//
//	@Test
//	void testAddGame() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateStock() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteGame() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteGameById() {
//		fail("Not yet implemented");
//	}

}
