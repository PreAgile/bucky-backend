package dev.buckybackend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Studio {

    @Id
    @GeneratedValue
    @Column(name = "studio_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int min_price;

    @Column(nullable = false)
    private int max_price;

    @Embedded
    private Option option;

    @Column(nullable = false)
    private Character parking;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String homepage;

    private String instagram;

    private String naver;

    private String facebook;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Character is_delete;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<StudioAddress> studioAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<StudioPhone> studioPhones = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<MenuBoard> menuBoards = new ArrayList<>();
}
