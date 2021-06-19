package dev.buckybackend.api.image;

import dev.buckybackend.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ImageReadApiController {

    private final ImageService imageService;

    @GetMapping("/api/v1/images")
    public Result getImages() {
        List<ImageListDto> collect = imageService.findImages().stream()
                .map(m -> new ImageListDto(m.getId(), m.getStudio().getId(), m.getImage_url()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T images;
    }

    @Data
    @AllArgsConstructor
    static class ImageListDto {
        private Long image_id;
        private Long studio_id;
        private String image_url;
    }
}
