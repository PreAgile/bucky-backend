package dev.buckybackend.domain;

import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private boolean outdoor;

    @Column(nullable = false)
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false, columnDefinition = "char(1) default 'N'")
    private Character is_delete;

    private Character is_release;

    private LocalDateTime create_time;
    private LocalDateTime update_time;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ImageLike imageLike;

    /** 연관 관계 메서드 **/
    public void setStudio(Studio studio) {
        this.studio = studio;
        studio.getImages().add(this);
    }


}
