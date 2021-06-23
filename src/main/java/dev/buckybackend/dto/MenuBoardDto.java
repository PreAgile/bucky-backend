package dev.buckybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class MenuBoardDto {
    @NotEmpty
    private String product_name;
    private int price;
    @NotEmpty
    private String description;
}