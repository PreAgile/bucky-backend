package dev.buckybackend.service;

import dev.buckybackend.domain.Studio;
import dev.buckybackend.domain.StudioAddress;
import dev.buckybackend.domain.StudioPhone;
import dev.buckybackend.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;

    //스튜디오 등록
    @Transactional
    public Long register(Studio studio) {
        validateDuplicateStudio(studio);
        studioRepository.save(studio);
        return studio.getId();
    }

    //스튜디오 수정
    @Transactional
    public void update(Long studioId, Studio studio) {
        validateDuplicateStudioExceptId(studio, studioId);
        Studio findStudio = validateExistStudio(studioId);

        findStudio.setName(studio.getName());

        findStudio.setMin_price(studio.getMin_price());
        findStudio.setMax_price(studio.getMax_price());

        findStudio.setHomepage(studio.getHomepage());
        findStudio.setInstagram(studio.getInstagram());
        findStudio.setName(studio.getName());
        findStudio.setFacebook(studio.getFacebook());

        findStudio.setDescription(studio.getDescription());

        findStudio.setOption(studio.getOption());
        findStudio.setParking(studio.getParking());

        findStudio.setUpdate_time(studio.getUpdate_time());
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
        for (StudioAddress studioAddress : studioAddressList) {
            findStudio.addStudioAddresses(studioAddress);
        }
        return id;
    }

    //TODO: 스튜디오 주소 업데이트

    //스튜디오 주소 조회
    public List<StudioAddress> findAddresses(Long id) {
        Studio findStudio = validateExistStudio(id);
        return findStudio.getStudioAddresses();
    }

    //TODO: 스튜디오 전화번호 업데이트

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

}
