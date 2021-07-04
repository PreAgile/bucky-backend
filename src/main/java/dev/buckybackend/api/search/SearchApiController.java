package dev.buckybackend.api.search;

import dev.buckybackend.domain.Studio;
import dev.buckybackend.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchApiController {
    private final StudioService studioService;

    @GetMapping("/api/v1/search/studios")
    public SearchResult searchStudios(@RequestParam(required = false, defaultValue = "") String query) {
        List<Studio> findStudio = studioService.findStudiosByNameAndIsDelete(query, 'N');
        List<StudioListDto> collect = findStudio.stream().map(s -> new StudioListDto(s.getId(), s.getName()))
                .collect(Collectors.toList());
        return new SearchResult(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class SearchResult<T> {
        private int count;
        private T studios;
    }

    @Data
    @AllArgsConstructor
    static class StudioListDto {
        private Long studio_id;
        private String name;
    }
}
