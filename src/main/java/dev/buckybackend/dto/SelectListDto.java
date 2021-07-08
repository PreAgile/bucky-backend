package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SelectListDto {

    private Long user_id;
    private Long image_id;
}
