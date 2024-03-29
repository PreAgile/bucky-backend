package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import dev.buckybackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudioService {

    private final StudioAddressRepository addressRepository;
    private final StudioPhoneRepository phoneRepository;
    private final MenuBoardRepository menuBoardRepository;
    private final UserRepository userRepository;

    @Autowired
    private StudioRepository studioRepository;
    @Autowired
    private ImageRepository imageRepository;

    //스튜디오 등록
    @Transactional
    public Long register(Studio studio, Long userId) {
        validateDuplicateStudio(studio);
        Optional<User> findUser = userRepository.findById(userId);

        studioRepository.save(studio);
        findUser.ifPresent(u -> studio.setUser(u));
        return studio.getId();
    }

    //스튜디오 수정
    @Transactional
    public Long update(Long studioId, Studio studio, Long userId) {
        validateDuplicateStudioExceptId(studio, studioId);
        Studio findStudio = studioRepository.getById(studioId);

        findStudio.setName(studio.getName());

        findStudio.setMin_price(studio.getMin_price());
        findStudio.setMax_price(studio.getMax_price());

        findStudio.setHomepage(studio.getHomepage());
        findStudio.setInstagram(studio.getInstagram());
        findStudio.setName(studio.getName());
        findStudio.setKakao(studio.getKakao());

        findStudio.setDescription(studio.getDescription());

        findStudio.setOption(studio.getOption());

        Optional<User> findUser = userRepository.findById(userId);
        findUser.ifPresent(u -> findStudio.setUser(u));
        return findStudio.getId();
    }

    //스튜디오 삭제(비활성화)
    @Transactional
    public void delete(Long id) {
        Studio findStudio = studioRepository.getById(id);
        findStudio.setIsDelete('Y');
    }

    //중복 스튜디오 검증
    private void validateDuplicateStudio(Studio studio) {
        List<Studio> studioList = studioRepository.findByName(studio.getName());
        if (!studioList.isEmpty()) {
            throw new IllegalStateException("Duplicate Name");
        }
    }

    //특정 Id를 제외한 중복 스튜디오 유무 검증
    private void validateDuplicateStudioExceptId(Studio studio, Long Id) {
        List<Studio> studioList = studioRepository.findByName(studio.getName());
        for( Studio findStudio : studioList ) {
            if (findStudio.getId() != Id) {
                throw new IllegalStateException("Duplicate Name");
            }
        }
    }

    //전체 스튜디오 조회
    public List<Studio> findStudios() {
        return studioRepository.findByIsDelete('N');
    }

    //전체 스튜디오 조회(pagination)
    public Page<Studio> findStudiosByNameAndIsDeletePageable(String name, Character isDelete, Pageable pageable) {
        return studioRepository.findByNameContainsIgnoreCaseAndIsDelete(name, isDelete, pageable);
    }

    //특정 스튜디오 조회
    public Studio findStudio(Long id) {
        return studioRepository.getById(id);
    }

    public List<Studio> findStudiosByNameAndIsDelete(String name, Character isDelete) {
        return studioRepository.findByNameContainsIgnoreCaseAndIsDelete(name, isDelete);
    }

    //스튜디오 주소 등록
    @Transactional
    public Long addStudioAddresses(Long id, List<StudioAddress> studioAddressList) {
        Studio findStudio = studioRepository.getById(id);
        for (StudioAddress sa : studioAddressList) {
            findStudio.addStudioAddresses(sa);
        }
        return findStudio.getId();
    }

    //스튜디오 주소 업데이트
    @Transactional
    public Long updateStudioAddresses(Long id, List<StudioAddress> studioAddressList) {
        Studio findStudio = studioRepository.getById(id);
        addressRepository.deleteByStudioId(findStudio.getId());
        for (StudioAddress sa : studioAddressList) {
            findStudio.addStudioAddresses(sa);
        }
        return findStudio.getId();
    }

    //스튜디오 주소 조회
    public List<StudioAddress> findAddresses(Long id) {
        Studio findStudio = studioRepository.getById(id);
        return findStudio.getStudioAddresses();
    }

    //스튜디오 전화번호 업데이트
    @Transactional
    public Long updateStudioPhones(Long id, List<StudioPhone> studioPhoneList) {
        Studio findStudio = studioRepository.getById(id);
        phoneRepository.deleteByStudioId(findStudio.getId());
        for (StudioPhone sp : studioPhoneList) {
            findStudio.addStudioPhones(sp);
        }
        return findStudio.getId();
    }

    //스튜디오 전화번호 등록
    @Transactional
    public Long addStudioPhones(Long id, List<StudioPhone> studioPhoneList) {
        Studio findStudio = studioRepository.getById(id);
        for (StudioPhone studioPhone : studioPhoneList) {
            findStudio.addStudioPhones(studioPhone);
        }
        return findStudio.getId();
    }

    //스튜디오 전화번호 조회
    public List<StudioPhone> findPhones(Long id) {
        Studio findStudio = studioRepository.getById(id);
        return findStudio.getStudioPhones();
    }

    //메뉴 정보 등록
    @Transactional
    public Long addMenuBoard(Long id, List<MenuBoard> menuBoardList) {
        Studio findStudio = studioRepository.getById(id);
        for (MenuBoard menuBoard : menuBoardList) {
            findStudio.addMenuBoard(menuBoard);
        }
        return findStudio.getId();
    }

    //메뉴 정보 수정
    @Transactional
    public Long updateMenuBoard(Long id, List<MenuBoard> menuBoardList) {
        Studio findStudio = studioRepository.getById(id);
        menuBoardRepository.deleteByStudioId(findStudio.getId());
        for (MenuBoard mb : menuBoardList) {
            findStudio.addMenuBoard(mb);
        }
        return findStudio.getId();
    }

    //메뉴 정보 조회
    public List<MenuBoard> findMenuBoard(Long id) {
        Studio findStudio = studioRepository.getById(id);
        return findStudio.getMenuBoards();
    }

    //이미지 정보 조회
    public List<Image> findImages(Long id) {
        Studio findStudio = studioRepository.getById(id);
        return findStudio.getImages();
    }

    //스튜디오별 이미지 조회
    public Page<Image> findImagesIsDelete(Long id, Character isDelete, Pageable pageable) {
        Studio findStudio = studioRepository.getById(id);
        return imageRepository.findByStudioAndIsDelete(findStudio, isDelete, pageable);
    }
}
