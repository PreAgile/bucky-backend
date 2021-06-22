package dev.buckybackend.serviceImpl;

import dev.buckybackend.domain.*;
import dev.buckybackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    private Image image;

    private Studio studio;

    private User user1;

    private User user2;

    @BeforeEach
    void settingEntity() {
        //스튜디오 세팅
        studio = new Studio();
        studio.setName("강남 어느 스튜디오");
        studio.setMin_price(30);
        studio.setMax_price(100);
        studio.setIs_delete('N');
        Option options = new Option();
        options.setHair_makeup(false);
        options.setRent_clothes(false);
        options.setTanning(false);
        options.setWaxing(false);
        studio.setOption(options);
        studio.setParking(false);
        studio.setDescription("HAHAHAHA");
        studio.setIs_delete('N');

        // 이미지 세팅
        image = new Image();
        image.setPeople_num(PeopleNum.MANY);
        image.setSex(Sex.F);
        image.setColor(Color.ACHROMATIC);
        image.setOutdoor(true);
        image.setImage_url("www.bucky.com");
        image.setStudio(studio);
        image.setIs_delete('N');
        image.setIs_release('N');
        image.setCreate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        //유저1 세팅
        user1 = new User();
        user1.setId(1L);
        user1.setCreate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        user1.setName("테스트1");
        user1.setEmail("test1@naver.com");
        user1.setMemo("테스트1 유저");
        user1.setRecent_login_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        user1.setRole(Role.USER);
        user1.setStudio(studio);

        //유저2 세팅
        user2 = new User();
        user2.setId(2L);
        user2.setCreate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        user2.setName("테스트2");
        user2.setEmail("test2@naver.com");
        user2.setMemo("테스트2 유저");
        user2.setRecent_login_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        user2.setRole(Role.USER);
        user2.setStudio(studio);
    }

    @Test
    @DisplayName("유저를 저장할수 있는가")
    void saveUser() {
        User saveUser = userRepository.save(user1);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("유저의 아이디로 유저를 조회한다")
    void getUserById() {
        User saveUser = userRepository.save(user1);
        Optional<User> findSaveUserById = userRepository.findById(saveUser.getId());
        assertThat(saveUser.getId()).isEqualTo(findSaveUserById.get().getId());
    }

    @Test
    @DisplayName("유저를 저장한뒤 해당 유저의 ID로 조회한 데이터를 업데이트 한다")
    void updateUser() {
        User saveUser = userRepository.save(user1);
        String originUserName = saveUser.getName();
        Long originUserId = saveUser.getId();

        saveUser.setName("바뀐 유저");
        saveUser.setCreate_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        saveUser.setEmail("changeUser@naver.com");
        saveUser.setMemo("테스트로 바뀐 유저");
        saveUser.setRecent_login_time(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        saveUser.setRole(Role.MANAGER);
        saveUser.setStudio(studio);

        User updateUser = userRepository.save(saveUser);

        assertThat(originUserId).isEqualTo(updateUser.getId());
        assertThat(originUserName).isNotEqualTo(updateUser.getName());
    }

    @Test
    @DisplayName("유저를 조회하고 해당 유저의 ID로 데이터를 삭제한다")
    void deleteUser() {
        User saveUser = userRepository.save(user1);
        User user = userRepository.findById(saveUser.getId()).get();
        userRepository.deleteById(user.getId());

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> {
            Optional<User> findEmptyUser = userRepository.findById(saveUser.getId());
            findEmptyUser.get();
        });

    }

    @Test
    @DisplayName("둘이상의 유저를 조회한다")
    void getAllUsers() {
        // Error!!
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> findAllUser = userRepository.findAll();
        assertThat(findAllUser.size()).isGreaterThan(1);
    }
}