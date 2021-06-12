package dev.buckybackend.service;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.repository.StudioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

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
        options.setHair_makeup('N');
        options.setRent_clothes('N');
        options.setTanning('N');
        options.setWaxing('N');
        studio.setOption(options);

        studio.setParking('Y');
        studio.setIs_delete('Y');

        studio.setCreate_time(LocalDateTime.now());
        studio.setUpdate_time(LocalDateTime.now());

        //when
        Long saveId = studioService.register(studio);

        //then
        assertEquals(studio, studioRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_스튜디오_예외() throws Exception {
        //given
        Studio studio1 = new Studio();
        Studio studio2 = new Studio();
        //----
        studio1.setName("버키스튜디오");

        Option options1 = new Option();
        options1.setHair_makeup('N');
        options1.setRent_clothes('N');
        options1.setTanning('N');
        options1.setWaxing('N');
        studio1.setOption(options1);

        studio1.setParking('Y');
        studio1.setIs_delete('Y');
        //----
        studio2.setName("버키스튜디오");

        Option options2 = new Option();
        options2.setHair_makeup('N');
        options2.setRent_clothes('N');
        options2.setTanning('N');
        options2.setWaxing('N');
        studio2.setOption(options2);

        studio2.setParking('Y');
        studio2.setIs_delete('Y');

        //when
        studioService.register(studio1);
        studioService.register(studio2); //exception

        //then
        fail("예외가 발생해야 한다.");
    }
}