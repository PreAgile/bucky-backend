package dev.buckybackend.api.image;

import dev.buckybackend.domain.Color;
import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.PeopleNum;
import dev.buckybackend.service.ImageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ImageCreateApiController {

    private final ImageService imageService;

    @PostMapping("/api/v1/images")
    public CreateImageResponse saveImage(@RequestBody @Valid CreateImageRequest request) {
        Image image = new Image();
        image.setPeople_num(request.getPeople_num());
        image.setSex(request.getSex());
        image.setColor(request.getColor());
        image.setOutdoor(request.getOutdoor());
        image.setImage_url(request.getImage_url());

        image.setCreate_time(request.getCreate_time());
        image.setIs_delete('N');

        //TODO:

        Long id = imageService.upload(image);
        return new CreateImageResponse(id);
    }

    @Data
    static class CreateImageRequest {
        @NotEmpty
        private PeopleNum people_num;
        @NotEmpty
        private Character sex;
        @NotEmpty
        private Color color;
        @NotEmpty
        private Character outdoor;
        @NotEmpty
        private String image_url;
        @NotEmpty
        private Long studio_id;

        private LocalDateTime update_time;
        private LocalDateTime create_time;

        private Character is_release;
    }

    @Data
    static class CreateImageResponse {
        private Long id;

        public CreateImageResponse(Long id) {
            this.id = id;
        }
    }
}
