package dev.buckybackend.service;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.dto.StudioSelectNumDto;
import dev.buckybackend.repository.StudioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudioServiceTest {

    @Autowired
    StudioService studioService;

    @Autowired
    StudioRepository studioRepository;

    @Test
    public void 스튜디오_등록() throws Exception {
        //given
        Studio studio = new Studio();
        studio.setName("버키스튜디오");

        Option options = new Option();

        options.setHairMakeup(false);
        options.setRentClothes(false);
        options.setTanning(false);
        options.setWaxing(false);
        options.setParking(true);
        studio.setOption(options);

        studio.setIsDelete('Y');

        studio.setCreateTime(LocalDateTime.now());
        studio.setUpdate_time(LocalDateTime.now());

        //when
        Long saveId = studioService.register(studio, 7L);

        //then
        assertEquals(studio, studioRepository.getById(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_스튜디오_예외() throws Exception {
        //given
        Studio studio1 = new Studio();
        Studio studio2 = new Studio();
        //----
        studio1.setName("버키스튜디오");

        Option options1 = new Option();
        options1.setHairMakeup(false);
        options1.setRentClothes(false);
        options1.setTanning(false);
        options1.setWaxing(false);
        options1.setParking(true);
        studio1.setOption(options1);

        studio1.setIsDelete('Y');
        //----
        studio2.setName("버키스튜디오");

        Option options2 = new Option();
        options2.setHairMakeup(false);
        options2.setRentClothes(false);
        options2.setTanning(false);
        options2.setWaxing(false);
        options2.setParking(true);
        studio2.setOption(options2);

        studio2.setIsDelete('Y');

        //when
        studioService.register(studio1, 1L);
        studioService.register(studio2, 2L); //exception

        //then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 유저별_스튜디오_좋아요수_조회() {
        List<StudioSelectNumDto> studio = studioRepository.findStudioImageLikeNumByUser(1775712869L);

        studio.size();
    }
}