package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AddressListDto {
    @NotEmpty
    private String address;
    @NotEmpty
    private Character is_main;
}
