package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(name = "IX_studio_address_1", columnList = "studio_id", unique = false)})
@Getter @Setter
public class StudioAddress {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @Id
    private String address;

    private Character is_main;

}
