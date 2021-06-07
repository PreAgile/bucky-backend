package dev.buckybackend.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
public class Option {

    private Character hair_makeup;

    private Character rent_clothes;

    private Character tanning;

    private Character waxing;

    protected Option() {
    }

    public Option(Character hair_makeup, Character rent_clothes, Character tanning, Character waxing) {
        this.hair_makeup = hair_makeup;
        this.rent_clothes = rent_clothes;
        this.tanning = tanning;
        this.waxing = waxing;
    }
}
