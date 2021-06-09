package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter @Setter
public class Option {

    @Column(nullable = false)
    private Character hair_makeup;

    @Column(nullable = false)
    private Character rent_clothes;

    @Column(nullable = false)
    private Character tanning;

    @Column(nullable = false)
    private Character waxing;

}
