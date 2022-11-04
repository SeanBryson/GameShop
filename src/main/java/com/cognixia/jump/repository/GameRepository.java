package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

	public Optional<Game> findByName(String name);
}
