package com.meli.adn.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meli.adn.app.domain.Sequence;
import com.meli.adn.app.dto.Stadistic;
import com.meli.adn.app.services.MutantService;
import com.meli.adn.app.services.StadisticsService;
import com.meli.adn.app.validators.SequenceValidator;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


@SpringBootTest
public class MutantControllerTest {
	
	@Autowired
    private MutantController controller;
	
	@Mock
    private MutantService mutantService;

    @Mock
    private SequenceValidator validator;

    @Mock
    private StadisticsService stadisticsService;
	
	@Test
    public void testCreateIsMutantWithNullDNAParameters() throws Exception {
        Sequence sequence = new Sequence();
        Mockito.doThrow(new IllegalArgumentException()).when(validator).validate(sequence);
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.create(sequence));
    }

    @Test
    public void testCreateIsMutantWithInvalidSizeParameters() throws Exception {
        String[] dnaList = {"ATGCGA", "CAGTGC", "TTATGT"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.doThrow(new IllegalArgumentException()).when(validator).validate(sequence);
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.create(sequence));
    }

    @Test
    public void testCreateIsMutantTrue() throws Exception {
        String[] dnaList = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.when(mutantService.save(sequence)).thenReturn(sequence);
        Mockito.when(mutantService.isMutant(sequence)).thenReturn(CompletableFuture.completedFuture(true));
        ResponseEntity responseEntity = controller.create(sequence);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateIsMutantFalse() throws Exception {
        String[] dnaList = {"TTGCGA","CAGTGC","TTATGT","AGAAGG","CACCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.when(mutantService.save(sequence)).thenReturn(sequence);
        Mockito.when(mutantService.isMutant(sequence)).thenReturn(CompletableFuture.completedFuture(false));
        ResponseEntity responseEntity = controller.create(sequence);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateStatistics() throws Exception {
        Stadistic statistic = new Stadistic(Float.valueOf(40), Float.valueOf(100), (float) 0.4);
        Mockito.when(stadisticsService.getStadistics()).thenReturn(CompletableFuture.completedFuture(statistic));
        ResponseEntity responseEntity = controller.findStatistics();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Stadistic result = (Stadistic) responseEntity.getBody();
        Assertions.assertEquals(0, result.getCountMutantDNA().intValue());
    }

}
