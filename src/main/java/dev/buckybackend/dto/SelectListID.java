package dev.buckybackend.dto;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectListID implements Serializable {
    private User user;
    private Image image;
}
