package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(indexes = {@Index(name = "IX_studio_phone_1", columnList = "studio_id")})
@Getter @Setter
public class StudioPhone {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @Id
    private String phone;

    private Character is_main;
}
