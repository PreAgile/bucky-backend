package dev.buckybackend.api.studio;

import dev.buckybackend.domain.Studio;
import dev.buckybackend.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StudioReadApiController {

    private final StudioService studioService;

    @GetMapping("/api/v1/studios")
    public Result getStudios() {
        List<StudioListDto> collect = studioService.findStudios().stream()
                .map(m -> new StudioListDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v1/studios/{id}")
    public StudioDto getStudio(@PathVariable("id") Long id) {
        Studio findStudio = studioService.findStudio(id);
        return new StudioDto(findStudio.getName(),
                findStudio.getMin_price(),
                findStudio.getMax_price(),
                findStudio.getHomepage(),
                findStudio.getInstagram(),
                findStudio.getNaver(),
                findStudio.getFacebook(),
                findStudio.getDescription(),
                findStudio.getOption().getHair_makeup(),
                findStudio.getOption().getRent_clothes(),
                findStudio.getOption().getTanning(),
                findStudio.getOption().getWaxing(),
                findStudio.getParking(),
                findStudio.getIs_delete());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class StudioListDto {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class StudioDto {
        private String name;

        private int min_price;
        private int max_price;

        private String homepage;
        private String instagram;
        private String naver;
        private String facebook;

        private String description;

        private Character hair_makeup;
        private Character rent_clothes;
        private Character tanning;
        private Character waxing;
        private Character parking;

        private Character is_deleted;
    }
}
