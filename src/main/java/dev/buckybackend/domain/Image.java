package dev.buckybackend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.aspectj.weaver.ast.Var;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Image {

    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NonNull
    private PeopleNum people_num;

    @NonNull
    private Character sex;

    @NonNull
    private String color;

    @NonNull
    private Character outdoor;

    @NonNull
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @NonNull
    private Character is_delete;

    private LocalDateTime create_time;
}
