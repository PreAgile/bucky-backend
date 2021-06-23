package dev.buckybackend.service;

import dev.buckybackend.domain.*;
import dev.buckybackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioAddressRepository addressRepository;
    private final StudioPhoneRepository phoneRepository;
    private final MenuBoardRepository menuBoardRepository;
    private final UserRepository userRepository;

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
    public void update(Long studioId, Studio studio, Long userId) {
        validateDuplicateStudioExceptId(studio, studioId);
        Studio findStudio = validateExistStudio(studioId);

        findStudio.setName(studio.getName());

        findStudio.setMin_price(studio.getMin_price());
        findStudio.setMax_price(studio.getMax_price());

        findStudio.setHomepage(studio.getHomepage());
        findStudio.setInstagram(studio.getInstagram());
        findStudio.setName(studio.getName());
        findStudio.setKakao(studio.getKakao());

        findStudio.setDescription(studio.getDescription());

        findStudio.setOption(studio.getOption());
        findStudio.setParking(studio.isParking());

        Optional<User> findUser = userRepository.findById(userId);
        findUser.ifPresent(u -> findStudio.setUser(u));
    }

    //스튜디오 삭제(비활성화)
    @Transactional
    public void delete(Long id) {
        Studio findStudio = validateExistStudio(id);
        findStudio.setIs_delete('Y');
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

    //스튜디오 등록 유무 검증
    private Studio validateExistStudio(Long id) {
        Studio findStudio = studioRepository.findOne(id);
        if (findStudio == null) {
            throw new IllegalStateException("Not Exist Key");
        }
        else {
            return findStudio;
        }
    }

    //전체 스튜디오 조회
    public List<Studio> findStudios() {
        return studioRepository.findAll();
    }

    //특정 스튜디오 조회
    public Studio findStudio(Long id) {
        return studioRepository.findOne(id);
    }

    //스튜디오 주소 등록
    @Transactional
    public Long addStudioAddresses(Long id, List<StudioAddress> studioAddressList) {
        Studio findStudio = validateExistStudio(id);
        for (StudioAddress sa : studioAddressList) {
            findStudio.addStudioAddresses(sa);
        }
        return findStudio.getId();
    }

    //스튜디오 주소 업데이트
    @Transactional
    public Long updateStudioAddresses(Long id, List<StudioAddress> studioAddressList) {
        Studio findStudio = validateExistStudio(id);
        addressRepository.deleteByStudioId(findStudio.getId());
        for (StudioAddress sa : studioAddressList) {
            findStudio.addStudioAddresses(sa);
        }
        return findStudio.getId();
    }

    //스튜디오 주소 조회
    public List<StudioAddress> findAddresses(Long id) {
        Studio findStudio = validateExistStudio(id);
        return findStudio.getStudioAddresses();
    }

    //스튜디오 전화번호 업데이트
    @Transactional
    public Long updateStudioPhones(Long id, List<StudioPhone> studioPhoneList) {
        Studio findStudio = validateExistStudio(id);
        phoneRepository.deleteByStudioId(findStudio.getId());
        for (StudioPhone sp : studioPhoneList) {
            findStudio.addStudioPhones(sp);
        }
        return findStudio.getId();
    }

    //스튜디오 전화번호 등록
    @Transactional
    public Long addStudioPhones(Long id, List<StudioPhone> studioPhoneList) {
        Studio findStudio = validateExistStudio(id);
        for (StudioPhone studioPhone : studioPhoneList) {
            findStudio.addStudioPhones(studioPhone);
        }
        return id;
    }

    //스튜디오 전화번호 조회
    public List<StudioPhone> findPhones(Long id) {
        Studio findStudio = validateExistStudio(id);
        return findStudio.getStudioPhones();
    }

    //메뉴 정보 등록
    @Transactional
    public Long addMenuBoard(Long id, List<MenuBoard> menuBoardList) {
        Studio findStudio = validateExistStudio(id);
        for (MenuBoard menuBoard : menuBoardList) {
            findStudio.addMenuBoard(menuBoard);
        }
        return id;
    }

    //메뉴 정보 수정
    @Transactional
    public Long updateMenuBoard(Long id, List<MenuBoard> menuBoardList) {
        Studio findStudio = validateExistStudio(id);
        menuBoardRepository.deleteByStudioId(findStudio.getId());
        for (MenuBoard mb : menuBoardList) {
            findStudio.addMenuBoard(mb);
        }
        return findStudio.getId();
    }

    //메뉴 정보 조회
    public List<MenuBoard> findMenuBoard(Long id) {
        Studio findStudio = validateExistStudio(id);
        return findStudio.getMenuBoards();
    }
}
