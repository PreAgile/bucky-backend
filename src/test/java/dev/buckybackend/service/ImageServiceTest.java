package dev.buckybackend.service;

import dev.buckybackend.domain.*;
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

        PageRequest pageable = PageRequest.of(0, 10);
        List<Studio> studios = studioRepository.findByFilter("test",
                'N',
                option.getHairMakeup(),
                option.getRentClothes(),
                option.getTanning(),
                option.getWaxing(),
                option.getParking());

        //when
//        Page<Image> images = imageRepository.findByPeopleNumAndSexAndColorAndOutdoorAndStudioIn(people_num, sex, color, outdoor, studios, pageable);
        Page<Image> images = imageRepository.findByFilterAndStudio(people_num, sex, color, outdoor, studios, pageable);

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

}
