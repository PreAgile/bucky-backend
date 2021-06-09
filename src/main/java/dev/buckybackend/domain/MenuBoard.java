package dev.buckybackend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
public class MenuBoard implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Id
    @Column(nullable = false)
    private Integer people_num;

    private Integer basic_price;

    private Integer standard_price;

    private Integer premium_price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuBoard menuBoard = (MenuBoard) o;
        return studio.equals(menuBoard.studio) && people_num.equals(menuBoard.people_num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studio, people_num);
    }
}
