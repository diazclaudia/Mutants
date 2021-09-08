package com.meli.adn.app.services.impl;


import com.meli.adn.app.domain.Sequence;
import com.meli.adn.app.repository.SequenceRepository;
import com.meli.adn.app.services.MutantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


@Service
public class MutantServiceImpl implements MutantService {
    
    @Autowired
    private SequenceRepository sequenceRepository;

    private static final String ADN_BASE_A = "AAAA";
    private static final String ADN_BASE_T = "TTTT";
    private static final String ADN_BASE_G = "GGGG";
    private static final String ADN_BASE_C = "CCCC";
    private static final Integer MIN_TOTAL_SEQUENCE = 2;
    private static final Integer TOTAL_SIZE_SEQUENCE = 4;

    @Override
    public Sequence save(@Valid Sequence sequence) {
    	return sequenceRepository.save(sequence);
    }

	@Override
    public Sequence findByDna(@Valid String[] dna) {
    	return sequenceRepository.findByDna(dna);
    }
	
	
	
	/**	 
		explanation of the algorithm:
		1. validate mutant sequences per row
		2. validate mutant sequences per column
		3. validate the low triangular diagonals
		4. validate the inverse low triangular diagonals
		5. validate the upper triangular diagonals
		6. validate the inverse upper triangular diagonals	
	 * **/
	/**
	 * create the array
	 * check columns and rows and redirect to the right method
	 * 
	 * **/
    @Async
    @Override
    public CompletableFuture<Boolean> isMutant(Sequence sequence) {
        char[][] dna = createArray(sequence.getDna());//convert string to array
        int total = 0;
        total = checkColumnsAndRows(total, dna);
        total = total < MIN_TOTAL_SEQUENCE ? checkLowerTriangular(total, dna) : total;
        total = total < MIN_TOTAL_SEQUENCE ? checkInverseLowerTriangular(total, dna) : total;
        total = total < MIN_TOTAL_SEQUENCE ? checkUpperTriangular(total, dna) : total;
        total = total < MIN_TOTAL_SEQUENCE ? checkInverseUpperTriangular(total, dna) : total;
        return CompletableFuture.completedFuture(total >= MIN_TOTAL_SEQUENCE);
    }

    private int checkColumnsAndRows(final int partialTotal, char[][] dna) {
        int total = partialTotal;
        for (int i = 0; i < dna.length; i++) {//rows        	
                total = total < MIN_TOTAL_SEQUENCE ? checkRows(dna, total, i) : total;//validates horizontal sequence
                if (total < MIN_TOTAL_SEQUENCE) {
                    StringBuilder column = new StringBuilder();
                    for (int j = 0; j < dna.length; j++) {//columns
                        column.append(dna[j][i]);
                    }
                    total = isValidSequence(column.toString()) ? ++total : total;
                    if(total >= MIN_TOTAL_SEQUENCE) break;
                } else {
                    break;
                }
            }
        return total;
    }
	/**
	 * validates secuence by rows
	 * **/
    private int checkRows(char[][] dna, int total, int i) {
        String row = String.valueOf(dna[i]);
        if (isValidSequence(row)) {
            total++;
        }
        return total;
    }

	/**
	 * validates diagonal by lower triangular
	 * **/
    private int checkLowerTriangular(final int partialTotal, final char[][] dna) {
        int total = partialTotal;
        for (int i = dna.length - TOTAL_SIZE_SEQUENCE; i >= 0; i--) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < dna.length - i; j++) {
                diagonal.append(dna[i + j][j]);
            }
            total = isValidSequence(diagonal.toString()) ? ++total : total;
            if(total >= MIN_TOTAL_SEQUENCE) break;
        }
        return total;
    }

    /**
	 * validates diagonal by upper triangular
	 * **/
    private int checkUpperTriangular(final int partialTotal, final char[][] dna) {
        int total = partialTotal;
        for (int i = 1; i <= dna.length -TOTAL_SIZE_SEQUENCE; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < dna.length - i; j++) {
                diagonal.append(dna[j][i + j]);
            }
            total = isValidSequence(diagonal.toString()) ? ++total : total;
            if(total >= MIN_TOTAL_SEQUENCE) break;
        }
        return total;
    }

    /**
	 * validates diagonal by inverse upper triangular
	 * **/
    private int checkInverseUpperTriangular(final int partialTotal, final char[][] dna) {
        int total = partialTotal;
        for (int i = dna.length - 1; i >= TOTAL_SIZE_SEQUENCE - 1; i--) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                diagonal.append(dna[i - j][j]);
            }
            total = isValidSequence(diagonal.toString()) ? ++total : total;
            if(total >= MIN_TOTAL_SEQUENCE) break;
        }
        return total;
    }

    /**
	 * validates diagonal by inverse lower triangular
	 * **/
    private int checkInverseLowerTriangular(final int partialTotal, final char[][] dna) {
        int total = partialTotal;
        for (int i = 0; i < dna.length - TOTAL_SIZE_SEQUENCE; i++) {
            StringBuilder diagonal = new StringBuilder();
            int count = 1;
            for (int j = dna.length - 1; j > 0 && i+count < dna.length ; j--) {
                diagonal.append(dna[j][i+count]);
                count++;
            }
            total = isValidSequence(diagonal.toString()) ? ++total : total;
            if(total >= MIN_TOTAL_SEQUENCE) break;
        }
        return total;
    }

    /**
     * Create an array with provided dna sequence
     */
    private char[][] createArray(String[] dnaList) {
        char[][] mutant;
        int sizePerColumn = dnaList[0].length();
        int sizePerRows = dnaList.length;
        mutant = new char[sizePerRows][sizePerColumn];
        for (int i = 0; i < sizePerRows; i++) {
            if (sizePerColumn != dnaList[i].length()) {
                throwIllegalArgumentException("Each element in the array must have the same size.");
            }
            char[] row = dnaList[i].toCharArray();
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'A' || row[j] == 'T' || row[j] == 'C' || row[j] == 'G') {
                    mutant[i][j] = row[j];
                } else {
                    throwIllegalArgumentException("Invalid argument. It must contains A, T, C or G");
                }
            }
        }
        return mutant;
    }

    private void throwIllegalArgumentException(String message) {
        throw new IllegalArgumentException(message);
    }

    /**
     * Check if the specified sequence has valid values
     * @param sequence values provided by the user
     * @return false if it is not valid
     */
    private boolean isValidSequence(String sequence) {
    	//System.out.println(" sequence "+sequence);
        boolean isValidSequence = false;
        if (sequence.contains(ADN_BASE_T) ||
            sequence.contains(ADN_BASE_G) ||
            sequence.contains(ADN_BASE_A) ||
            sequence.contains(ADN_BASE_C)) {
            isValidSequence = true;
        }
        return isValidSequence;
    }
}
