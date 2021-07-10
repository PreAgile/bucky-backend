package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import dev.buckybackend.repository.ImageLikeRepository;
import dev.buckybackend.repository.ImageRepository;
import dev.buckybackend.repository.SelectListRepository;
import dev.buckybackend.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudioRepository studioRepository;
    private final ImageLikeRepository imageLikeRepository;
    private final SelectListRepository selectListRepository;

    //이미지 업로드
    @Transactional
    public Long upload(Image image, Long studioId) {
        Studio findStudio = studioRepository.getById(studioId);
        image.setStudio(findStudio);
        imageRepository.save(image);
        //Image를 업로드 하는 시점에 ImageLikeTable생성
        //imageLikeRepository.initImageLikeNum(image.getId());
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

    //스튜디오 삭제(비활성화)
    @Transactional
    public void delete(Long id) {
        Image findImage = imageRepository.getById(id);
        findImage.setIsDelete('Y');
    }

    //전체 이미지 조회
    public List<Image> findImages() {
        return imageRepository.findAll();
    }
    //스튜디오 정보로 이미지 조회
    public List<Image> findImagesByStudio(Studio studio) {
        return imageRepository.findByStudio(studio);
    }

    //이미지 조회
    public Image findImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        AtomicReference<Image> returnImage = new AtomicReference<>();
        image.ifPresentOrElse(returnImage::set, () -> {
            throw new NoSuchElementException("이미지가 없습니다 ID : " + id);
        });
        return returnImage.get();
    }

    //필터값으로 이미지 조회
    public Page<Image> findImagesByFilterAndStudio(PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor, List<Studio> studio, Pageable pageable) {
        return imageRepository.findByFilterAndStudio(peopleNum, sex, color, outdoor, studio, pageable);

    }

    //스튜디오 & 필터값으로 이미지 조회
    public Page<Image> findImagesByFilter(String studioName, Option studioOption, PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor, Integer[] minPrice, Integer[] maxPrice, Pageable pageable) {
        List<Studio> findStudio = studioRepository.findByFilter(studioName,
                'N',
                studioOption.getHairMakeup(),
                studioOption.getRentClothes(),
                studioOption.getTanning(),
                studioOption.getWaxing(),
                studioOption.getParking(),
                minPrice,
                maxPrice);
        return this.findImagesByFilterAndStudio(
                peopleNum,
                sex,
                color,
                outdoor == true ? null : false,
                findStudio,
                pageable);
    }

    //유사한 컨셉의 이미지 조회
    public Page<Image> findImagesSimilar(Long imageId, Pageable pageable) {
        Image findImage = imageRepository.getById(imageId);
        return imageRepository.findByPeopleNumAndSexAndColorAndOutdoorAndIsDeleteAndIdNot(
                findImage.getPeopleNum(),
                findImage.getSex(),
                findImage.getColor(),
                findImage.isOutdoor(),
                'N',
                findImage.getId(),
                pageable
        );
    }

    @Transactional
    public void plusImageLikeNum(Long userId, Long imageId) {
        imageLikeRepository.plusImageLikeNum(imageId);
        selectListRepository.createById(userId,imageId);
    }

    @Transactional
    public void minusImageLikeNum(Long userId, Long imageId) {
        imageLikeRepository.minusImageLikeNum(imageId);
        selectListRepository.deleteById(userId, imageId);
    }

    @Transactional
    public ImageLike getImageLikeNum(Long imageId) {
        return imageLikeRepository.getImageLikeNum(imageId);
    }

}
