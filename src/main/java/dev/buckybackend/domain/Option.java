package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter @Setter
public class Option {

    private Character hair_makeup;

    private Character rent_clothes;

    private Character tanning;

    private Character waxing;

}
