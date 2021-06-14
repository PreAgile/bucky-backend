package dev.buckybackend.service;

import dev.buckybackend.domain.Studio;
import dev.buckybackend.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(Long id, Studio studio) {
        Studio findStudio = validateExistStudio(id);
        validateDuplicateStudio(findStudio);
        studio.setIs_delete(findStudio.getIs_delete());
        studioRepository.save(studio);
    }

    //스튜디오 삭제(비활성화)
    @Transactional
    public void delete(Long id) {
        Studio findStudio = validateExistStudio(id);
        findStudio.setIs_delete('Y');
        studioRepository.save(findStudio);
    }

    //중복 스튜디오 검증
    private void validateDuplicateStudio(Studio studio) {
        List<Studio> studioList = studioRepository.findByName(studio.getName());
        if (!studioList.isEmpty()) {
            throw new IllegalStateException("Duplicate Name");
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
}
