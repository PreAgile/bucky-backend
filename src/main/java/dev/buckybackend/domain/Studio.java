package dev.buckybackend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Studio {

    @Id
    @GeneratedValue
    @Column(name = "studio_id")
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @OneToOne(mappedBy = "studio", fetch = FetchType.LAZY)
    private StudioAddress studioAddress;

    @NonNull
    @Embedded
    private Option option;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String homepage;

    private String instagram_id;

    //TODO: 미확정
    @NonNull
    private int min_price;

    @NonNull
    private int max_price;

    @NonNull
    private String phone;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

}
