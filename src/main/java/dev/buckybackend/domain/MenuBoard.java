package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(indexes = {@Index(name = "IX_menu_board_1", columnList = "studio_id")})
@Getter @Setter
public class MenuBoard {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @Id
    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

}
