package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studio_address")
@Getter @Setter
@IdClass(StudioAddress.class)
public class StudioAddress implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    private String address;
}
