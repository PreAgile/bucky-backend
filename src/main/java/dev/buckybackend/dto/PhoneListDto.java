package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class PhoneListDto {
    @NotEmpty
    private String phone;
    @NotEmpty
    private Character is_main;
}