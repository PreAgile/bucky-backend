package dev.buckybackend.api.image;

import dev.buckybackend.domain.*;
import dev.buckybackend.dto.ImageLikeDto;
import dev.buckybackend.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/api/v1/images/{imageId}/like/{userId}")
    public UpdateImageResponse plusImageLikeNum(@PathVariable("userId") Long userId, @PathVariable("imageId") Long imageId){
        imageService.plusImageLikeNum(userId, imageId);
        ImageLike imageLikeNum = imageService.getImageLikeNum(imageId);
        // TODO: LikeNum이 그전값으로 나옴 확인 필요
        return new UpdateImageResponse(imageLikeNum.getImage().getId());
    }

    @DeleteMapping("/api/v1/images/{imageId}/unlike/{userId}")
    public UpdateImageResponse minusImageLikeNum(@PathVariable("userId") Long userId, @PathVariable("imageId") Long imageId) {
        imageService.minusImageLikeNum(userId,imageId);
        ImageLike imageLikeNum = imageService.getImageLikeNum(imageId);
        // TODO: LikeNum이 그전값으로 나옴 확인 필요
        return new UpdateImageResponse(imageLikeNum.getImage().getId());
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
