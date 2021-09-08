package com.meli.adn.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@JsonIgnoreProperties
public class Sequence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@NotNull
	@Column(unique=true)
    private String[] dna;

	@NotNull
    private Boolean mutant;

    public Sequence() {
    }

    public Sequence(Integer id, Boolean mutant) {
        this.id = id;
        this.mutant = mutant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public Boolean getMutant() {
        return mutant;
    }

    public void setMutant(Boolean mutant) {
        this.mutant = mutant;
    }
}