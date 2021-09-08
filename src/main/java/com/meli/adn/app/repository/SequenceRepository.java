package com.meli.adn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.meli.adn.app.domain.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, Integer> {
	
	@Query("Select s from Sequence s where s.dna like ?1 ")
	public Sequence findByDna(String[] keyword);
	
	@Query("Select count(s.mutant) from Sequence s where s.mutant = True")
	public Integer countMutant() ;
	
	@Query("Select count(s.mutant) from Sequence s where s.mutant = False")
	public Integer countHuman();
	
}
