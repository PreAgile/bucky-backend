package dev.buckybackend.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeopleNum people_num;

    @Column(nullable = false)
    private Character sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private Character outdoor;

    @Column(nullable = false)
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false, columnDefinition = "char(1) default 'N'")
    private Character is_delete;

    private Character is_release;

    private LocalDateTime create_time;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private ImageLike imageLike;

}
