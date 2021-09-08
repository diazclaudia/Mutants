package com.meli.adn.app.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.adn.app.domain.Sequence;

@SpringBootTest
public class SequenceValidatorTest {

	 	private SequenceValidator validator = new SequenceValidator();
	    
	    @Test
	    public void testValidateNullSequence() {
	        Sequence sequence = new Sequence();
	        Assertions.assertThrows(IllegalArgumentException.class, () -> validator.validate(sequence));	  
	    }

	    @Test
	    public void testValidateInvalidSequence() {
	        Sequence sequence = new Sequence();
	        String dna[] = {"ATGCGA","CAGTGC","CAGTGC"};
	        sequence.setDna(dna);
	        Assertions.assertThrows(IllegalArgumentException.class, () -> validator.validate(sequence));	       
	    }

	    @Test
	    public void testValidateSuccessSequence() {
	        Sequence sequence = new Sequence();
	        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
	        sequence.setDna(dna);
	        Assertions.assertThrows(IllegalArgumentException.class, () -> validator.validate(sequence));	  
	    }
	    
}
