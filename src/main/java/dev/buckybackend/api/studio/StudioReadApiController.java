package dev.buckybackend.api.studio;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.dto.AddressListDto;
import dev.buckybackend.dto.ImageDto;
import dev.buckybackend.dto.MenuBoardDto;
import dev.buckybackend.dto.PhoneListDto;
import dev.buckybackend.service.StudioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @GetMapping("/api/v1/studios/all")
    public StudioResult getStudios() {
        List<StudioNameDto> collect = studioService.findStudios().stream()
//                .filter(f -> f.getIs_delete() == 'N')
                .map(m -> new StudioNameDto(
                        m.getId(),
                        m.getName()
                ))
                .collect(Collectors.toList());
        return new StudioResult(collect.size(), collect);
    }

    /**
     * 전체 스튜디오 조회(pagination)
     * @return
     */
    @GetMapping("/api/v1/studios")
    public StudioPageResult getStudiosPageable(@RequestParam("name") String name,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) {
        PageRequest sPage = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Studio> findStudio = studioService.findStudiosByNameOrderByCreateTimeDesc(name, sPage);

        List<StudioPageDto> collect = findStudio.stream()
//                .filter(f -> f.getIs_delete() == 'N')
                .map(m -> new StudioPageDto(
                        m.getId(),
                        m.getName(),
                        m.getStudioAddresses().stream()
                                .filter(a -> a.getIs_main() == 'Y')
                                .map(o -> o.getAddress())
                                .collect(Collectors.joining("")),
                        m.getStudioPhones().stream()
                                .filter(a -> a.getIs_main() == 'Y')
                                .map(o -> o.getPhone())
                                .collect(Collectors.joining("")),
                        m.getImages().size(),
                        m.getCreateTime(),
                        m.getUpdate_time(),
                        m.getIs_release(),
                        m.getRelease_time()
                ))
                .collect(Collectors.toList());
        return new StudioPageResult(collect.size(), findStudio.getTotalPages(), collect);
    }

    /**
     * 특정 스튜디오 조회
     * @param id
     * @return
     */
    @GetMapping("/api/v1/studios/{id}")
    public StudioDetailDto getStudio(@PathVariable("id") Long id) {
        Studio findStudio = studioService.findStudio(id);
        return new StudioDetailDto(findStudio.getName(),
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
                findStudio.getOption().isParking(),
                findStudio.getIsDelete());
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

    /**
     * 스튜디오별 Image조회
     * @param id
     * @return
     */
    @GetMapping("/api/v1/studios/{id}/images")
    public ImagesResult getStudioImages(@PathVariable("id") Long id) {
        List<ImageDto> collect = new ArrayList<>();
        for (Image i : studioService.findImages(id)) {
            ImageDto imageDto = new ImageDto(i.getId(), i.getPeople_num(), i.getSex(),
                    i.getColor(), i.isOutdoor(), i.getImage_url(),
                    i.getStudio().getId(),i.getCreate_time(),i.getUpdate_time(),
                    i.getIs_delete(), i.getIs_release());
            collect.add(imageDto);
        }
        return new ImagesResult(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class StudioPageResult<T> {
        private int count;
        private int last_page;
        private T studios;
    }

    @Data
    @AllArgsConstructor
    static class StudioResult<T> {
        private int count;
        private T studios;
    }

    @Data
    @AllArgsConstructor
    static class StudioPageDto {
        private Long studio_id;
        private String name;
        private String address;
        private String phone;
        private int total_images;
        private LocalDateTime create_time;
        private LocalDateTime update_time;
        private Character is_release;
        private LocalDateTime release_time;
    }

    @Data
    @AllArgsConstructor
    static class StudioNameDto {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class StudioDetailDto {
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
    }

    @Data
    @AllArgsConstructor
    static class AddressResult<T> {
        private int count;
        private T address;
    }

    @Data
    @AllArgsConstructor
    static class PhoneResult<T> {
        private int count;
        private T phones;
    }

    @Data
    @AllArgsConstructor
    static class MenuBoardResult<T> {
        private int count;
        private T menu_board;
    }

    @Data
    @AllArgsConstructor
    static class ImagesResult<T> {
        private int count;
        private T images;
    }
}
