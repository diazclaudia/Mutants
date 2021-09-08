package com.meli.adn.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meli.adn.app.domain.Sequence;
import com.meli.adn.app.dto.Stadistic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


@SpringBootTest
public class StadisticsServiceTest {

	@Autowired
    private StadisticsService service;
	
	@Mock
    private StadisticsService serviceMock;
		
	@Mock
    private MutantService mutantService;

    @Test
    public void testGetStadistics() throws InterruptedException, ExecutionException {        
        CompletableFuture<Stadistic> isMutant = service.getStadistics();
        Assertions.assertTrue(isMutant.get().getRation() != null);
    }
    
   
}
