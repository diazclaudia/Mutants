package com.meli.adn.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stadistic {

    @JsonProperty("count_mutant_dna")
    private Float countMutantDNA;

    @JsonProperty("count_human_dna")
    private Float countHumanDNA;

    Float ration;

    public Stadistic(Float countMutantDNA, Float countHumanDNA, Float ration) {
        this.countMutantDNA = countMutantDNA;
        this.countHumanDNA = countHumanDNA;
        this.ration = ration;
    }

    public Float getCountMutantDNA() {
        return countMutantDNA;
    }

    public void setCountMutantDNA(Float countMutantDNA) {

        this.countMutantDNA = countMutantDNA;
    }

    public Float getCountHumanDNA() {
        return countHumanDNA;
    }

    public void setCountHumanDNA(Float countHumanDNA) {
        this.countHumanDNA = countHumanDNA;
    }

    public Float getRation() {
        return ration;
    }

    public void setRation(Float ration) {
        this.ration = ration;
    }
}
