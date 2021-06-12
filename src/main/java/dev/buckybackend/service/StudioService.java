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

    //중복 스튜디오 검증
    private void validateDuplicateStudio(Studio studio) {
        List<Studio> studioList = studioRepository.findByName(studio.getName());
        if (!studioList.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 스튜디오입니다.");
        }
    }

    //전체 스튜디오 조회
    public List<Studio> findStudios() {
        return studioRepository.findAll();
    }

    //특정 스튜디오 조회
    public Studio findStudio(Long studioId) {
        return studioRepository.findOne(studioId);
    }
}
