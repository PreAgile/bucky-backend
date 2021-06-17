package dev.buckybackend.api.studio;

import dev.buckybackend.domain.MenuBoard;
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

    /**
     * 전체 스튜디오 조회
     * @return
     */
    @GetMapping("/api/v1/studios")
    public StudioResult getStudios() {
        List<StudioListDto> collect = studioService.findStudios().stream()
                .map(m -> new StudioListDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
        return new StudioResult(collect.size(), collect);
    }

    /**
     * 특정 스튜디오 조회
     * @param id
     * @return
     */
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

    /**
     * 스튜디오 주소 조회
     * @param id
     * @return
     */
    @GetMapping("/api/v1/studios/{id}/addresses")
    public AddressResult getAddresses(@PathVariable("id") Long id) {
        List<AddressListDto> collect = studioService.findAddresses(id).stream()
                .map(m -> new AddressListDto(m.getAddress(), m.getIs_main()))
                .collect(Collectors.toList());
        return new AddressResult(collect.size(), collect);
    }

    @GetMapping("/api/v1/studios/{id}/phones")
    public PhoneResult getPhones(@PathVariable("id") Long id) {
        List<PhoneListDto> collect = studioService.findPhones(id).stream()
                .map(m -> new PhoneListDto(m.getPhone(), m.getIs_main()))
                .collect(Collectors.toList());
        return new PhoneResult(collect.size(), collect);
    }

    @GetMapping("/api/v1/studios/{id}/menus")
    public MenuBoardResult getMenus(@PathVariable("id") Long id) {
        List<MenuBoardDto> collect = studioService.findMenuBoard(id).stream()
                .map(m -> new MenuBoardDto(m.getProduct_name(), m.getPrice(), m.getDescription()))
                .collect(Collectors.toList());
        return new MenuBoardResult(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class StudioResult<T> {
        private int count;
        private T studios;
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

    @Data
    @AllArgsConstructor
    static class AddressResult<T> {
        private int count;
        private T address;
    }

    @Data
    @AllArgsConstructor
    static class AddressListDto {
        private String address;
        private Character is_main;
    }

    @Data
    @AllArgsConstructor
    static class PhoneResult<T> {
        private int count;
        private T phones;
    }

    @Data
    @AllArgsConstructor
    static class PhoneListDto {
        private String phone;
        private Character is_main;
    }

    @Data
    @AllArgsConstructor
    static class MenuBoardResult<T> {
        private int count;
        private T menu_board;
    }

    @Data
    @AllArgsConstructor
    static class MenuBoardDto {
        private String product_name;
        private int price;
        private String description;
    }
}
