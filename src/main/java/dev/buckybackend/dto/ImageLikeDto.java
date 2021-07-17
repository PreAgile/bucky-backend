package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ImageLikeDto {

    private Long image_id;

    private int like_num;

}
