package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressListDto {
    private String address;
    private Character is_main;
}
