package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudioSelectNumDto {
    Long studio_id;
    String studio_name;
    Long like_num;
}
