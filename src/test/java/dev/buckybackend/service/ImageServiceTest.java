package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import dev.buckybackend.dto.ImageListDto;
import dev.buckybackend.repository.ImageRepository;
import dev.buckybackend.repository.StudioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageServiceTest {

    @Autowired
    StudioRepository studioRepository;

    @Autowired
    ImageRepository imageRepository;

    @Test
    public void 이미지_조회() throws Exception {
        //given
        PeopleNum[] people_num = {PeopleNum.ONE, PeopleNum.TWO};
        Sex[] sex = null;
        Color[] color = {Color.ACHROMATIC, Color.VIVID};
        Boolean outdoor = null;

        Option option = new Option();
        option.setHairMakeup(true);
        option.setRentClothes(true);
        option.setTanning(false);
        option.setWaxing(false);
        option.setParking(true);

        Integer[] min_price = {0};
        Integer[] max_price = {100000};
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<ImageListDto> images = imageRepository.findByFilterAndStudio(
                "test",
                'N',
                option.getHairMakeup(),
                option.getRentClothes(),
                option.getTanning(),
                option.getWaxing(),
                option.getParking(),
                min_price,
                max_price,
                people_num,
                sex,
                color,
                outdoor,
                pageable);

        //then
         images.getTotalElements();
    }

    @Test
    public void 스튜디오별_이미지_조회() throws Exception {
        //given
        Studio findStudio = studioRepository.getById(9L);
        //when
        List<Image> findImage = imageRepository.findByStudio(findStudio);
        //then
        findImage.size();
    }

    @Test
    public void 같은_컨셉_이미지_조회() throws Exception {
        //given
        Image findImage = imageRepository.getById(19L);
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        Page<Image> relatedImage = imageRepository.findByPeopleNumAndSexAndColorAndOutdoorAndIsDeleteAndIdNot(
                findImage.getPeopleNum(),
                findImage.getSex(),
                findImage.getColor(),
                findImage.isOutdoor(),
                'N',
                findImage.getId(),
                pageable
        );
        //then
        relatedImage.getTotalElements();
    }
}
