package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import dev.buckybackend.repository.ImageRepository;
import dev.buckybackend.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudioRepository studioRepository;

    //이미지 업로드
    @Transactional
    public Long upload(Image image, Long studioId) {
        Studio findStudio = studioRepository.getById(studioId);
        image.setStudio(findStudio);
        imageRepository.save(image);
        return image.getId();
    }

    //이미지 수정
    @Transactional
    public Long update(Long imageId, Image image) {
        Image findImage = imageRepository.getById(imageId);
        findImage.setPeopleNum(image.getPeopleNum());
        findImage.setSex(image.getSex());
        findImage.setColor(image.getColor());
        findImage.setOutdoor(image.isOutdoor());
        findImage.setImage_url(image.getImage_url());
        return findImage.getId();
    }

    //전체 이미지 조회
    public List<Image> findImages() {
        return imageRepository.findAll();
    }

    //스튜디오 정보로 이미지 조회
    public List<Image> findImagesByStudio(Studio studio) {
        return imageRepository.findByStudio(studio);
    }

    //필터값으로 이미지 조회
    public Page<Image> findImagesByFilterAndStudio(PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor, List<Studio> studio, Pageable pageable) {
//        return imageRepository.findByPeopleNumAndSexAndColorAndOutdoorAndStudioIn(peopleNum, sex, color, outdoor, studio, pageable);
        return imageRepository.findByFilterAndStudio(peopleNum, sex, color, outdoor, studio, pageable);

    }

    //스튜디오 & 필터값으로 이미지 조회
    public Page<Image> findImagesByFilter(String studioName, Option studioOption, PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor, Pageable pageable) {
        List<Studio> findStudio = studioRepository.findByNameContainsIgnoreCaseAndIsDeleteAndOption(studioName,
                'N',
                studioOption.getHairMakeup(),
                studioOption.getRentClothes(),
                studioOption.getTanning(),
                studioOption.getWaxing(),
                studioOption.getParking());
        return this.findImagesByFilterAndStudio(
                peopleNum,
                sex,
                color,
                outdoor == true ? null : false,
                findStudio,
                pageable);
    }
}
