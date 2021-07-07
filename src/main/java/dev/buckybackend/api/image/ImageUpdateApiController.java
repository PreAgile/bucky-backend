package dev.buckybackend.api.image;

import dev.buckybackend.domain.*;
import dev.buckybackend.dto.ImageLikeDto;
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

    /**
     * 이미지 수정 API
     * @param id
     * @param request
     * @return
     */
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

    @PutMapping("/api/v1/images/{id}/plus")
    public ImageLikeDto plusImageLikeNum(@PathVariable("id") Long id) {
        imageService.plusImageLikeNum(id);
        ImageLike imageLikeNum = imageService.getImageLikeNum(id);
        // TODO: LikeNum이 그전값으로 나옴 확인 필요
        return new ImageLikeDto(imageLikeNum.getImage().getId(), imageLikeNum.getLike_num()+ 1);
    }

    @PutMapping("/api/v1/images/{id}/minus")
    public ImageLikeDto minusImageLikeNum(@PathVariable("id") Long id) {
        imageService.minusImageLikeNum(id);
        ImageLike imageLikeNum = imageService.getImageLikeNum(id);
        // TODO: LikeNum이 그전값으로 나옴 확인 필요
        return new ImageLikeDto(imageLikeNum.getImage().getId(), imageLikeNum.getLike_num() - 1);
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
