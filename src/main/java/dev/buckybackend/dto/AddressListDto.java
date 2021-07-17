package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressListDto {
    @NotEmpty
    private String address;
    @NotEmpty
    private Character is_main;
}
