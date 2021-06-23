package dev.buckybackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private boolean parking;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String homepage;

    private String instagram;

    private String naver;

    private String kakao;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private Character is_release;

    @Column(nullable = false, columnDefinition = "char(1) default 'N'")
    private Character is_delete;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<StudioAddress> studioAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<StudioPhone> studioPhones = new ArrayList<>();

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<MenuBoard> menuBoards = new ArrayList<>();

    @OneToOne(mappedBy = "studio", fetch = FetchType.EAGER)
    @JsonBackReference
    private User user = new User();

    /** 연관 관계 메서드 **/
    public void addStudioAddresses(StudioAddress studioAddress) {
        this.studioAddresses.add(studioAddress);
        studioAddress.setStudio(this);
    }

    public void addStudioPhones(StudioPhone studioPhone) {
        this.studioPhones.add(studioPhone);
        studioPhone.setStudio(this);
    }

    public void addMenuBoard(MenuBoard menuBoard) {
        this.menuBoards.add(menuBoard);
        menuBoard.setStudio(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.setStudio(this);
    }
}
