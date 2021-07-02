package dev.buckybackend.api.studio;

import dev.buckybackend.domain.*;
import dev.buckybackend.dto.AddressListDto;
import dev.buckybackend.dto.MenuBoardDto;
import dev.buckybackend.dto.PhoneListDto;
import dev.buckybackend.service.StudioService;
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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudioCreateApiController {

    private final StudioService studioService;

    /**
     * 스튜디오 등록
     *
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
        studio.setKakao(request.getKakao());

        studio.setDescription(request.getDescription());

        Option option = new Option();
        option.setHairMakeup(request.isHair_makeup());
        option.setRentClothes(request.isRent_clothes());
        option.setTanning(request.isTanning());
        option.setWaxing(request.isWaxing());
        option.setParking(request.isParking());

        studio.setOption(option);

        studio.setIsDelete('N'); //생성할 때는 Default N

        Long id = studioService.register(studio, request.getUser_id());
        return new CreateStudioResponse(id);
    }

    /**
     * 스튜디오 관리 페이지에서 업로드 클릭시 해당 스튜디오와 그에 따른 이미지들을 Upload(is_release 변경)
     *
     * @param studioIdList
     * @return
     */
    @PostMapping("/api/v1/studios/upload")
    public void uploadStudiosAndImages(@RequestBody @Valid uploadStudioIdDto[] studioIdList) {
        Optional<@Valid uploadStudioIdDto[]> list = Optional.ofNullable(studioIdList);
        list.ifPresentOrElse(i -> {
            for (uploadStudioIdDto input : i) {
                Studio studio = studioService.findStudio(input.getStudio_id());
                studio.setIs_release(input.getIs_release());
                studio.getImages().forEach(image -> image.setIs_release(input.getIs_release()));
                studio.setRelease_time(LocalDateTime.now());
                studioService.update(studio.getId(), studio, studio.getUser().getId());
            }
        }, () -> {
            throw new InputMismatchException("잘못된 값 입니다.");
        });
    }

    /**
     * 스튜디오 주소 등록
     *
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
     *
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

    @PostMapping("/api/v1/studios/{id}/menus")
    public CreateStudioResponse saveMenus(@PathVariable("id") Long id,
                                          @RequestBody @Valid MenuBoardDto[] request) {
        List<MenuBoard> menuBoardList = new ArrayList<>();
        for (MenuBoardDto menu : request) {
            MenuBoard menuBoard = new MenuBoard();
            menuBoard.setProduct_name(menu.getProduct_name());
            menuBoard.setPrice(menu.getPrice());
            menuBoard.setDescription(menu.getDescription());
            menuBoardList.add(menuBoard);
        }
        Long studioId = studioService.addMenuBoard(id, menuBoardList);
        return new CreateStudioResponse(studioId);
    }

    @Data
    static class CreateStudioRequest {

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

        private boolean hair_makeup; //getter isHair_makeup
        private boolean rent_clothes;
        private boolean tanning;
        private boolean waxing;
        private boolean parking;

        private LocalDateTime create_time;

        private Character is_release;
    }

    @Data
    static class CreateStudioResponse {
        private Long studio_id;

        public CreateStudioResponse(Long studio_id) {
            this.studio_id = studio_id;
        }
    }

    @Data
    static class uploadStudioIdDto {
        @NotEmpty
        private Long studio_id;
        @NotEmpty
        private Character is_release;
    }
}
