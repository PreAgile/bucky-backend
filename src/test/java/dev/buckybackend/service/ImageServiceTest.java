package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageServiceTest {

    @Autowired
    StudioService studioService;

    @Autowired
    ImageService imageService;

    @Test
    public void 이미지_조회() throws Exception {
        //given
        PeopleNum people_num = PeopleNum.ONE;
        Sex sex = null;
        Color color = Color.ACHROMATIC;
        boolean outdoor = true;

        //when
        List<Image> images = imageService.findImagesByFilter(people_num, sex, color, outdoor);

        //then
        images.size();
    }

    @Test
    public void 스튜디오별_이미지_조회() throws Exception {
        //given
        Studio findStudio = studioService.findStudio(9L);
        //when
        List<Image> findImage = imageService.findImagesByStudioId(findStudio);
        //then
        findImage.size();
    }

}
