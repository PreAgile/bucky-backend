package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter @Setter
public class ImageLike implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer like_num;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageLike imageLike = (ImageLike) o;
        return image.equals(imageLike.image) && like_num.equals(imageLike.like_num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, like_num);
    }
}
