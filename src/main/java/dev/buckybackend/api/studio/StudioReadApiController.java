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

    /**
     * 전체 스튜디오 조회(Paging)
     * @return
     */
    @GetMapping("/api/v1/studios/page/{count}")
    public StudioResult getStudios(@PathVariable("count") Integer count) {
        List<StudioDto> collect = studioService.findStudios().stream()
                .map(s -> new StudioDto(s.getId(),s.getName(), s.getMin_price(), s.getMax_price()
                        , s.getHomepage(), s.getInstagram(), s.getNaver(), s.getKakao(), s.getDescription()
                        , s.getOption().isHair_makeup(), s.getOption().isRent_clothes(), s.getOption().isTanning()
                        , s.getOption().isWaxing(), s.isParking(), s.getIs_delete(), s.getImages().size()))
                .collect(Collectors.toList());

        Integer lastPage = calculateLastPage(collect.size(), count);

        return new StudioResult(lastPage ,count, collect);
    }

    /**
     * 특정 스튜디오 조회
     * @param id
     * @return
     */
    @GetMapping("/api/v1/studios/{id}")
    public StudioDto getStudio(@PathVariable("id") Long id) {
        Studio findStudio = studioService.findStudio(id);
        return new StudioDto(findStudio.getId(),
                findStudio.getName(),
                findStudio.getMin_price(),
                findStudio.getMax_price(),
                findStudio.getHomepage(),
                findStudio.getInstagram(),
                findStudio.getNaver(),
                findStudio.getKakao(),
                findStudio.getDescription(),
                findStudio.getOption().isHair_makeup(),
                findStudio.getOption().isRent_clothes(),
                findStudio.getOption().isTanning(),
                findStudio.getOption().isWaxing(),
                findStudio.isParking(),
                findStudio.getIs_delete(),
                findStudio.getImages().size());
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
        private int lastPage;
        private int count;
        private T studios;
    }

    @Data
    @AllArgsConstructor
    static class StudioListDto {
        private Long studio_id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class StudioDto {
        private Long Id;
        private String name;

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

        private Character is_deleted;
        // 총 이미지 개수 추가
        private int totalImages;
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

    public Integer calculateLastPage(int collectSize, int count) {
        int portion = collectSize / count;
        int rest = collectSize % count;
        int lastPage;
        // Paging 계산
        if(rest != 0 && rest != collectSize) {
            lastPage = portion + 1;
        } else if(portion == 1 || count == 1){
            lastPage = portion;
        } else {
            throw new ArithmeticException("계산되지 않는 값입니다.");
        }
        return lastPage;
    }
}
