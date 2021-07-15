package dev.buckybackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.buckybackend.dto.SelectListID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "select_list")
@IdClass(SelectListID.class)
public class SelectList implements Serializable {

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @JsonBackReference
    private Image image;
}
