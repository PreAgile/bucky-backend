package dev.buckybackend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    @NonNull
    private Studio studio;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private LocalDateTime create_time;

    private LocalDateTime recent_login_time;

    @ManyToMany
    @JoinTable(name = "pick_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images = new ArrayList<>();
}
