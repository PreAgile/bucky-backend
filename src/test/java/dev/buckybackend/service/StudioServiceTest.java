package dev.buckybackend.service;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.repository.StudioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

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

        options.setHair_makeup(false);
        options.setRent_clothes(false);
        options.setTanning(false);
        options.setWaxing(false);
        studio.setOption(options);

        studio.setParking(true);
        studio.setIs_delete('Y');

        studio.setCreate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        studio.setUpdate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        //when
        Long saveId = studioService.register(studio, 7L);

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
        options1.setHair_makeup(false);
        options1.setRent_clothes(false);
        options1.setTanning(false);
        options1.setWaxing(false);
        studio1.setOption(options1);

        studio1.setParking(true);
        studio1.setIs_delete('Y');
        //----
        studio2.setName("버키스튜디오");

        Option options2 = new Option();
        options2.setHair_makeup(false);
        options2.setRent_clothes(false);
        options2.setTanning(false);
        options2.setWaxing(false);
        studio2.setOption(options2);

        studio2.setParking(true);
        studio2.setIs_delete('Y');

        //when
        studioService.register(studio1, 1L);
        studioService.register(studio2, 2L); //exception

        //then
        fail("예외가 발생해야 한다.");
    }
}