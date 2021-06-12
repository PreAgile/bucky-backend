package dev.buckybackend.api;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.service.StudioService;
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
public class StudioApiController {

    private final StudioService studioService;

    @PostMapping("/api/v1/studio")
    public CreateStudioResponse saveStudio(@RequestBody @Valid CreateStudioRequest request) {
        Studio studio = new Studio();
        studio.setName(request.getName());

        studio.setMin_price(request.getMin_price());
        studio.setMax_price(request.getMax_price());

        studio.setHomepage(request.getHomepage());
        studio.setInstagram(request.getInstagram());
        studio.setNaver(request.getNaver());
        studio.setFacebook(request.getFacebook());

        studio.setDescription(request.getDescription());

        Option option = new Option();
        option.setHair_makeup(request.getHair_makeup());
        option.setRent_clothes(request.getRent_clothes());
        option.setTanning(request.getTanning());
        option.setWaxing(request.getWaxing());

        studio.setOption(option);
        studio.setParking(request.getParking());

        studio.setCreate_time(request.getCreate_time());
        studio.setUpdate_time(request.getUpdate_time());
        studio.setIs_delete('N');
        studio.setIs_release('N');

        Long id = studioService.register(studio);
        return new CreateStudioResponse(id);
    }

    @Data
    static class CreateStudioRequest {

        @NotEmpty
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

        private LocalDateTime create_time;
        private LocalDateTime update_time;

    }

    @Data
    class CreateStudioResponse {
        private Long id;

        public CreateStudioResponse(Long id) {
            this.id = id;
        }
    }

}
