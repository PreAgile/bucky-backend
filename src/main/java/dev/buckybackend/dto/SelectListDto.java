package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SelectListDto {

    private Long user_id;
    private Long image_id;
    private String image_url;
    private Long studio_id;
    private String studio_name;
    private Character is_release;

}
