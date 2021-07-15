package dev.buckybackend.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "users")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id")
    @JsonManagedReference
    private Studio studio;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @CreatedDate
    private LocalDateTime create_time;

    @LastModifiedDate
    private LocalDateTime recent_login_time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SelectList> selectLists = new ArrayList<>();
}
