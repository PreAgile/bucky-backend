package dev.buckybackend.api.image;

import dev.buckybackend.domain.Color;
import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.PeopleNum;
import dev.buckybackend.domain.Sex;
import dev.buckybackend.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class ImageUpdateApiController {

    private final ImageService imageService;

    @PutMapping("/api/v1/images/{id}")
    public UpdateImageResponse updateImage(@PathVariable("id") Long id,
                                           @RequestBody @Valid UpdateImageRequest request) {
        Image image = new Image();
        image.setPeopleNum(request.getPeople_num());
        image.setSex(request.getSex());
        image.setColor(request.getColor());
        image.setOutdoor(request.isOutdoor());
        image.setImage_url(request.getImage_url());

        Long imageId = imageService.update(id, image);
        return new UpdateImageResponse(imageId);
    }

    @Data
    static class UpdateImageRequest {
        @NotNull
        private PeopleNum people_num;
        @NotNull
        private Sex sex;
        @NotNull
        private Color color;
        private boolean outdoor;
        @NotEmpty
        private String image_url;
    }

    @Data
    @AllArgsConstructor
    static class UpdateImageResponse {
        private Long image_id;
    }


}
