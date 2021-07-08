package dev.buckybackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_num", nullable = false)
    private PeopleNum peopleNum;

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
    @JsonBackReference
    private Studio studio;

    @Column(name = "is_delete", nullable = false, columnDefinition = "char(1) default 'N'")
    private Character isDelete;

    private Character is_release;

    @CreatedDate
    private LocalDateTime create_time;
    @LastModifiedDate
    private LocalDateTime update_time;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ImageLike imageLike;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<SelectList> selectLists = new ArrayList<>();

    /** 연관 관계 메서드 **/
    public void setStudio(Studio studio) {
        this.studio = studio;
        studio.getImages().add(this);
    }

}
