package com.meli.adn.app.services.impl;

import com.meli.adn.app.dto.Stadistic;
import com.meli.adn.app.repository.SequenceRepository;
import com.meli.adn.app.services.StadisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;


@Service
public class StadisticsServiceImpl implements StadisticsService {

    
    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
	public Integer countMutant() {
    	return sequenceRepository.countMutant();
    }
	
	@Override
	public Integer countHuman() {
    	return sequenceRepository.countHuman();
    }
	
	/**
	 * Calculate the stadistics
	 * 
	 * **/
	@Async
    @Override
    public CompletableFuture<Stadistic> getStadistics() {
		Float totalMutants = Float.valueOf(countMutant());
		Float totalHumans = Float.valueOf(countHuman()); 		
		Float ratio = Float.valueOf(0);
        
        if(totalMutants >= 0 && totalHumans > 0) {
            ratio = Float.valueOf((totalMutants / (totalHumans+totalMutants)));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return CompletableFuture.completedFuture(new Stadistic(Float.valueOf(totalMutants), Float.valueOf(totalHumans), Float.valueOf(df.format(ratio))));
    }
}
