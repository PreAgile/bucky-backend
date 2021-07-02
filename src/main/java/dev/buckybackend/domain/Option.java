package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Option {

    @Column(name = "hair_makeup", nullable = false)
    private Boolean hairMakeup;

    @Column(name = "rent_clothes", nullable = false)
    private Boolean rentClothes;

    @Column(nullable = false)
    private Boolean tanning;

    @Column(nullable = false)
    private Boolean waxing;

    @Column(nullable = false)
    private Boolean parking;
}
