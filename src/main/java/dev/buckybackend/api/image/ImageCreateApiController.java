package dev.buckybackend.api.image;

import dev.buckybackend.domain.Color;
import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.PeopleNum;
import dev.buckybackend.domain.Sex;
import dev.buckybackend.service.ImageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class ImageCreateApiController {

    private final ImageService imageService;

    @PostMapping("/api/v1/images")
    public CreateImageResponse saveImage(@RequestBody @Valid CreateImageRequest request) {
        Image image = new Image();
        image.setPeopleNum(request.getPeople_num());
        image.setSex(request.getSex());
        image.setColor(request.getColor());
        image.setOutdoor(request.isOutdoor());
        image.setImage_url(request.getImage_url());

        image.setIsDelete('N');

        Long id = imageService.upload(image, request.getStudio_id());
        return new CreateImageResponse(id);
    }

    @Data
    static class CreateImageRequest {
        @NotNull
        private PeopleNum people_num;
        @NotNull
        private Sex sex;
        @NotNull
        private Color color;
        private boolean outdoor;
        @NotEmpty
        private String image_url;
        @NotNull
        private Long studio_id;
    }

    @Data
    static class CreateImageResponse {
        private Long image_id;

        public CreateImageResponse(Long image_id) {
            this.image_id = image_id;
        }
    }
}
