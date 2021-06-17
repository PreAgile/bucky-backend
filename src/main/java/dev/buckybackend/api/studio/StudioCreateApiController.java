package dev.buckybackend.api.studio;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.domain.StudioAddress;
import dev.buckybackend.domain.StudioPhone;
import dev.buckybackend.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class StudioCreateApiController {

    private final StudioService studioService;

    /**
     * 스튜디오 등록
     * @param request
     * @return
     */
    @PostMapping("/api/v1/studios")
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

        studio.setCreate_time(LocalDateTime.now());
        studio.setUpdate_time(LocalDateTime.now());
        studio.setIs_delete('N'); //생성할 때는 Default N

        Long id = studioService.register(studio);
        return new CreateStudioResponse(id);
    }

    /**
     * 스튜디오 주소 등록
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/api/v1/studios/{id}/addresses")
    public CreateStudioResponse saveAddresses(@PathVariable("id") Long id,
                                               @RequestBody @Valid AddressListDto[] request) {
        List<StudioAddress> studioAddressList = new ArrayList<>();
        for (AddressListDto address : request) {
            StudioAddress studioAddress = new StudioAddress();
            studioAddress.setAddress(address.getAddress());
            studioAddress.setIs_main(address.getIs_main());
            studioAddressList.add(studioAddress);
        }
        Long studioId = studioService.addStudioAddresses(id, studioAddressList);
        return new CreateStudioResponse(studioId);
    }

    /**
     * 스튜디오 전화번호 등록
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/api/v1/studios/{id}/phones")
    public CreateStudioResponse savePhones(@PathVariable("id") Long id,
                                           @RequestBody @Valid PhoneListDto[] request) {
        List<StudioPhone> studioPhoneList = new ArrayList<>();
        for (PhoneListDto phone : request) {
            StudioPhone studioPhone = new StudioPhone();
            studioPhone.setPhone(phone.getPhone());
            studioPhone.setIs_main(phone.getIs_main());
            studioPhoneList.add(studioPhone);
        }
        Long studioId = studioService.addStudioPhones(id, studioPhoneList);
        return new CreateStudioResponse(studioId);
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

        @NotNull
        private Character hair_makeup;
        @NotNull
        private Character rent_clothes;
        @NotNull
        private Character tanning;
        @NotNull
        private Character waxing;
        @NotNull
        private Character parking;

        private LocalDateTime create_time;

        private Character is_release;
    }

    @Data
    static class CreateStudioResponse {
        private Long id;

        public CreateStudioResponse(Long id) {
            this.id = id;
        }
    }


    @Data
    @AllArgsConstructor
    static class AddressListDto {
        private String address;
        private Character is_main;
    }

    @Data
    @AllArgsConstructor
    static class PhoneListDto {
        private String phone;
        private Character is_main;
    }
}
