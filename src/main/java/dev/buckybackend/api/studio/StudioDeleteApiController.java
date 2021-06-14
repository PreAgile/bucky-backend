package dev.buckybackend.api.studio;

import dev.buckybackend.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudioDeleteApiController {
    private final StudioService studioService;

    @DeleteMapping("/api/v1/studios/{id}")
    public DeleteStudioResponse deleteStudio(@PathVariable("id") Long id) {
        studioService.delete(id);
        return new DeleteStudioResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class DeleteStudioResponse {
        private Long id;
    }
}
