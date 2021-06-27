package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Option {

    @Column(nullable = false)
    private boolean hair_makeup;

    @Column(nullable = false)
    private boolean rent_clothes;

    @Column(nullable = false)
    private boolean tanning;

    @Column(nullable = false)
    private boolean waxing;

    @Column(nullable = false)
    private boolean parking;
}
