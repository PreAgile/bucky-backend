package dev.buckybackend.dto;

import dev.buckybackend.domain.Color;
import dev.buckybackend.domain.PeopleNum;
import dev.buckybackend.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ImageDto {
    private Long image_id;
    private PeopleNum people_num;
    private Sex sex;
    private Color color;
    private boolean outdoor;
    private String image_url;
    private Long studio_id;
    private String studio_name;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private Character is_delete;
    private Character is_release;
}