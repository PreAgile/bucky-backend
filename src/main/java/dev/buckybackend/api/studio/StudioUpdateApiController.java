package dev.buckybackend.api.studio;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.domain.StudioAddress;
import dev.buckybackend.dto.AddressListDto;
import dev.buckybackend.service.StudioService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudioUpdateApiController {
    private final StudioService studioService;

    @PutMapping("/api/v1/studios/{id}")
    public UpdateStudioResponse updateStudio(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateStudioRequest request) {
        Studio studio = new Studio();
        studio.setName(request.getName());

        studio.setMin_price(request.getMin_price());
        studio.setMax_price(request.getMax_price());

        studio.setHomepage(request.getHomepage());
        studio.setInstagram(request.getInstagram());
        studio.setNaver(request.getNaver());
        studio.setKakao(request.getKakao());

        studio.setDescription(request.getDescription());

        Option option = new Option();
        option.setHair_makeup(request.isHair_makeup());
        option.setRent_clothes(request.isRent_clothes());
        option.setTanning(request.isTanning());
        option.setWaxing(request.isWaxing());

        studio.setOption(option);
        studio.setParking(request.isParking());

        studio.setUpdate_time(LocalDateTime.now());

        studioService.update(id, studio, request.getUser_id());

        Studio findStudio = studioService.findStudio(id);
        return new UpdateStudioResponse(findStudio.getId());
    }

    @PutMapping("/api/v1/studios/{id}/addresses")
    public UpdateStudioResponse updateAddresses(@PathVariable("id") Long id,
                                                @RequestBody @Valid AddressListDto[] request) {
        List<StudioAddress> studioAddressList = new ArrayList<>();
        for (AddressListDto address : request) {
            StudioAddress studioAddress = new StudioAddress();
            studioAddress.setAddress(address.getAddress());
            studioAddress.setIs_main(address.getIs_main());
            studioAddressList.add(studioAddress);
        }
        Long studioId = studioService.updateStudioAddresses(id, studioAddressList);
        return new UpdateStudioResponse(studioId);
    }

    @Data
    static class UpdateStudioRequest {
        @NotEmpty
        private String name;
        @NotNull
        private Long user_id;

        private int min_price;
        private int max_price;

        private String homepage;
        private String instagram;
        private String naver;
        private String kakao;

        private String description;

        private boolean hair_makeup;
        private boolean rent_clothes;
        private boolean tanning;
        private boolean waxing;
        private boolean parking;

        private LocalDateTime update_time;
    }

    @Data
    @AllArgsConstructor
    static class UpdateStudioResponse {
        private Long studio_id;
    }

}
