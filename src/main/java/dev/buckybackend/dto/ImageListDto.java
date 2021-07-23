package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageListDto {

    private Long image_id;
    private String image_url;
    private Long studio_id;
    private String studio_name;
    private Character is_release;
}

