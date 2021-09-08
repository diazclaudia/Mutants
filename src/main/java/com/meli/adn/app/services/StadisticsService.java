package com.meli.adn.app.services;

import org.springframework.stereotype.Component;

import com.meli.adn.app.dto.Stadistic;

import java.util.concurrent.CompletableFuture;

@Component
public interface StadisticsService {

	
    /**
     * Find how many mutants and humands be in the database
     */
	public Integer countMutant() ;
	
	public Integer countHuman();

	public CompletableFuture<Stadistic> getStadistics();

}