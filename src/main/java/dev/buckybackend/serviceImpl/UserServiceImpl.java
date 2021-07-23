package dev.buckybackend.serviceImpl;

import dev.buckybackend.domain.SelectList;
import dev.buckybackend.domain.User;
import dev.buckybackend.dto.ImageListDto;
import dev.buckybackend.dto.StudioSelectNumDto;
import dev.buckybackend.repository.SelectListRepository;
import dev.buckybackend.repository.StudioRepository;
import dev.buckybackend.repository.UserRepository;
import dev.buckybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudioRepository studioRepository;
    @Autowired
    private SelectListRepository selectListRepository;

    private EntityManager em;

    public void deleteByStudioId(Long studioId) {
        em.createQuery("delete from MenuBoard mb where mb.studio.id = :studio_id")
                .setParameter("studio_id", studioId)
                .executeUpdate();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse((u) -> {
            u.setRecent_login_time(LocalDateTime.now());
        }, () -> {
            throw new NoSuchElementException("해당 유저가 존재하지 않습니다 , ID 값 : " + id);
        });
        return user.get();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        saveUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(user.get().getId());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<ImageListDto> getSelectListDtoByUserId(Long id) {
        List<SelectList> selectLists = selectListRepository.findById(id);
        List<ImageListDto> images = new ArrayList<>();
        selectLists.forEach(s -> {
            ImageListDto selectListDto = new ImageListDto(
                    s.getImage().getId(),
                    s.getImage().getImage_url(),
                    s.getImage().getStudio().getId(),
                    s.getImage().getStudio().getName(),
                    s.getImage().getIs_release()
            );
            images.add(selectListDto);
        });
        return images;
    }

    public List<StudioSelectNumDto> findStudioLikeNumByUserId(Long userId) {
        return studioRepository.findStudioImageLikeNumByUser(userId);
    }

    @Override
    public void saveSelectListByImageId(Long userId, Long imageId) {
        selectListRepository.createById(userId,imageId);
    }

    @Override
    public void deleteSelectListByImageId(Long userId, Long imageId) {
        selectListRepository.deleteById(userId, imageId);
    }

    //중복 스튜디오 검증
    private void validateDuplicateUser(User user) {
        Optional<User> validUser = userRepository.findById(user.getId());
        validUser.ifPresent(u -> {
            if(u.getName().equals(user.getName())) {
                throw new IllegalStateException("Duplicate User Name = " + u.getName());
            }
        });

    }
}
