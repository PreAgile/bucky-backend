package dev.buckybackend.api.image;

import dev.buckybackend.common.Constant;
import dev.buckybackend.domain.*;
import dev.buckybackend.dto.ImageDto;
import dev.buckybackend.dto.ImageLikeDto;
import dev.buckybackend.dto.ImageListDto;
import dev.buckybackend.service.ImageService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ImageReadApiController {

    private final ImageService imageService;

    /**
     * 전체 이미지 조회
     * @return
     */
    @GetMapping("/api/v1/images/all")
    public Result getImages() {
        List<ImageListDto> collect = imageService.findImages().stream()
                .map(i -> new ImageListDto(
                        i.getId(),
                        i.getImage_url(),
                        i.getStudio().getId(),
                        i.getStudio().getName(),
                        i.getIs_release()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    /**
     * 이미지 조회
     * @return
     */
    @GetMapping("/api/v1/images/{id}")
    public ImageDto getImages(@PathVariable("id") Long id) {
        Image image = imageService.findImageById(id);
        return new ImageDto(image.getId(), image.getPeopleNum(), image.getSex(), image.getColor()
        ,image.isOutdoor(),image.getImage_url(),image.getStudio().getId(), image.getStudio().getName(),image.getCreate_time(),image.getUpdate_time()
        ,image.getIsDelete(),image.getIs_release());
    }

    /**
     * 필터값으로 전체 이미지 조회(pagination)
     * @return
     */
    @GetMapping("/api/v1/images")
    public ImagePageResult getImagesPageable(@RequestParam(required = false, defaultValue = "") String name,
                                             @RequestParam(required = false, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = Constant.MAIN_IMAGE_LIST_SIZE) Integer size,
                                             @RequestParam(required = false) PeopleNum[] people_num,       //image: multiple select
                                             @RequestParam(required = false) Sex[] sex,                    //image: multiple select
                                             @RequestParam(required = false) Color[] color,                //image: multiple select
                                             @RequestParam(required = false) Boolean outdoor,              //image
                                             @RequestParam(required = false) Boolean hair_makeup,          //studio
                                             @RequestParam(required = false) Boolean rent_clothes,         //studio
                                             @RequestParam(required = false) Boolean tanning,              //studio
                                             @RequestParam(required = false) Boolean waxing,               //studio
                                             @RequestParam(required = false) Boolean parking,              //studio
                                             @RequestParam(required = false) Integer[] min_price,          //studio: multiple select
                                             @RequestParam(required = false) Integer[] max_price) {        //studio: multiple select
        PageRequest iPage = PageRequest.of(page, size, Sort.by("create_time").descending()); //등록 최신순
        Option option = new Option();
        option.setHairMakeup(hair_makeup);
        option.setRentClothes(rent_clothes);
        option.setTanning(tanning);
        option.setWaxing(waxing);
        option.setParking(parking);

        Page<dev.buckybackend.dto.ImageListDto> collect = imageService.findImagesByFilter(name, option, people_num, sex, color, outdoor, min_price, max_price, iPage);

        return new ImagePageResult(Long.valueOf(collect.getTotalElements()).intValue(), collect.getTotalPages(), collect.getContent());
    }

    /**
     * 유사한 컨셉 이미지 조회 API
     * @param id
     * @return
     */
    @GetMapping("/api/v1/images/{id}/similar")
    public ImagePageResult getImagesSimilar(@PathVariable("id") Long id,
                                   @RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = Constant.DETAIL_IMAGE_LIST_SIZE) Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Image> findImage = imageService.findImagesSimilar(id, pageable);
        List<ImageListDto> collect = findImage.stream()
                .map(i -> new ImageListDto(
                        i.getId(),
                        i.getImage_url(),
                        i.getStudio().getId(),
                        i.getStudio().getName(),
                        i.getIs_release()))
                .collect(Collectors.toList());
        return new ImagePageResult(collect.size(), findImage.getTotalPages(), collect);
    }

    /**
     * 이미지 like num 조회
     * @param image_id
     * @return
     */
    @GetMapping("/api/v1/images/{image_id}/likes")
    public ImageLikeDto getImageLikeNum(@PathVariable("image_id") Long image_id) {
        ImageLike imageLikeNum = imageService.getImageLikeNum(image_id);
        return new ImageLikeDto(imageLikeNum.getImage().getId(), imageLikeNum.getLike_num());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T images;
    }

    @Data
    @AllArgsConstructor
    static class ImagePageResult<T> {
        private int count;
        private int last_page;
        private T images;
    }



}
