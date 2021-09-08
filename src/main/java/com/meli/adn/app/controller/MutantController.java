package com.meli.adn.app.controller;


import com.meli.adn.app.domain.Sequence;
import com.meli.adn.app.dto.Stadistic;
import com.meli.adn.app.services.StadisticsService;
import org.apache.catalina.connector.Response;

import com.meli.adn.app.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.meli.adn.app.validators.SequenceValidator;

import javax.validation.Valid;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @Autowired
    private StadisticsService stadisticsService;
    

    @Autowired
    private SequenceValidator validator;
    

    public MutantController(SequenceValidator validator, StadisticsService stadisticsService, MutantService mutantService) {
      this.validator = validator;
      this.stadisticsService = stadisticsService;
      this.mutantService = mutantService;
    }

    /**
     * Return 200 OK if the specified sequence is mutant otherwise 403
     * @param sequence array of sequence
     * @return Return 200 OK if the specified sequence is mutant, 400 BAD REQUEST if
     * sequence is invalid or 403 FORBIDDEN if it is not mutant
     * @throws InterruptedException 
     * @throws ExecutionException 
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@PostMapping(path = "/mutant")
    public ResponseEntity create(@RequestBody @Valid Sequence sequence) throws InterruptedException, ExecutionException {
        validator.validate(sequence);
        //validate if the sequence exists        
        Sequence existentSequence = mutantService.findByDna(sequence.getDna());
        if(existentSequence == null) {
            CompletableFuture<Boolean> isMutantFuture = mutantService.isMutant(sequence);
            Boolean isMutant = isMutantFuture.get();
            sequence.setMutant(isMutant);      
            mutantService.save(sequence);
        }
        if (existentSequence != null && existentSequence.getMutant() || (sequence.getMutant() != null && sequence.getMutant())) {        	
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }
    }

    /**
     *
     * @return Statistics information with among of mutants and total count human dna
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(path = "/stats")
    public ResponseEntity<Stadistic> findStatistics() throws ExecutionException, InterruptedException {    	
    	return ResponseEntity.ok().body(stadisticsService.getStadistics().get());
    }
}
