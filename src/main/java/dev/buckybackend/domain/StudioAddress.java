package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
public class StudioAddress implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Id
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudioAddress that = (StudioAddress) o;
        return studio.equals(that.studio) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studio, address);
    }
}
