package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognixia.jump.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

	// get a range of gpa and use jpql query notation
	@Query("SELECT u FROM Game u WHERE u.esrb != 'M' AND u.esrb != 'm'")
	public List<Game> findWithRating();
}
