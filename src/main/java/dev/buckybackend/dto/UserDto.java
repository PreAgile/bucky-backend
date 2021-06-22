package dev.buckybackend.dto;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.Role;
import dev.buckybackend.domain.Studio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    private Studio studio;
    private String memo;
    private Role role;
    private List<Image> images = new ArrayList<>();
}
