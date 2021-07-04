package dev.buckybackend.api.image;

import dev.buckybackend.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageDeleteApiController {
    private final ImageService imageService;

    /**
     * 이미지 삭제 API
     * @param id
     * @return
     */
    @DeleteMapping("/api/v1/images/{id}")
    public DeleteImageResponse deleteImage(@PathVariable("id") Long id) {
        imageService.delete(id);
        return new DeleteImageResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class DeleteImageResponse {
        private Long id;
    }
}
