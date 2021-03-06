package com.meli.adn.app.services;

import com.meli.adn.app.domain.Sequence;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;


@Component
public interface MutantService {

    /**
     * Check if the specified sequence is mutant
     * @param sequence array of sequence values
     *
     */
    CompletableFuture<Boolean> isMutant(Sequence sequence) throws InterruptedException;

    /**
     * Save the specified sequence
     */
    Sequence save(Sequence sequence);

    /**
     * Find existing sequence that match with the specified dna
     * @param sequence with dna to be matched
     * @return existent sequence
     */
    
	Sequence findByDna(@Valid String[] dna);
	
	
	
}


