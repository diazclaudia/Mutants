package com.meli.adn.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.adn.app.domain.Sequence;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;


@SpringBootTest
public class MutantServiceTest {

    private static final String[] DNA_1 = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private static final String[] DNA_2 = {"ATGCGA", "AAAACC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private static final String[] DNA_3 = {"ATGCGA", "CTGTGA", "TTATGA", "ATAAGA", "CTCCTA", "TTTTTG"};
    private static final String[] DNA_4 = {"AGGCGA", "CAGTGC", "TAAGGT", "AAAAGG", "CCCCTA", "TTTTAA"};
    private static final String[] DNA_5 = {"TTGCGA", "CAGTGC", "GTATGT", "AGTAGG", "CCTGTA", "TCACTG"};
    private static final String[] DNA_6 = {"GTCAGA", "CGTGAC", "ATGACG", "TGACTC", "GAAGGT", "AGTACC"};
    private static final String[] DNA_7 = {"GTCAGA", "CGTGAC", "ATGACG", "TGACTC", "GACGGT", "AGTACC"};
    private static final String[] DNA_8 = {"GTGATA", "CGATAC", "ATGACG", "TGACTC", "CACGGT", "AGTACC"};
    private static final String[] DNA_9 = {"TCCAGG", "CTCAGG", "CGTCGA", "ACGTCG", "ATCGTC", "TCACGA"};
    private static final String[] DNA_10 = {"ACTG","ACGT","CGAT","GTAC"};
    private static final String[] DNA_11 = {"ACCC","TTGT"};

    

    @Autowired
    private MutantService service;
    
    @Mock
    private MutantService serviceMock;

    @Test
    public void testIsMutantDiagonalAndHorizontalAndVertical() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_1);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }
    
    @Test
    public void testIsMutantHorizontal() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_2);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }
    
    @Test
    public void testIsMutantHorizontalAndVertical() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_3);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantLowerDiagonal() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_4);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }

    @Test
    public void testIsNotMutant()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_5);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertFalse(isMutant.get());
    }

    @Test
    public void testIsMutantVertical()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_6);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantLowerAndUpperDiagonal() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_7);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantInverseDiagonal()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_8);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantTransverseDiagonal()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_9);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertTrue(isMutant.get());
    }
    
    @Test
    public void testSmall()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_10);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertFalse(isMutant.get());
    }
    
    @Test
    public void testWrongSize()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA_11);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assertions.assertFalse(isMutant.get());
    }
    
    
      
 
}
