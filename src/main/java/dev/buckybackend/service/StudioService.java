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
        Studio findStudio = studioRepository.findOne(id);

        findStudio.setName(studio.getName());

        findStudio.setMin_price(studio.getMin_price());
        findStudio.setMax_price(studio.getMax_price());

        findStudio.setHomepage(studio.getHomepage());
        findStudio.setInstagram(studio.getInstagram());
        findStudio.setNaver(studio.getNaver());
        findStudio.setFacebook(studio.getFacebook());

        findStudio.setDescription(studio.getDescription());
        findStudio.setOption(studio.getOption());
        findStudio.setParking(studio.getParking());

        findStudio.setUpdate_time(studio.getUpdate_time());
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
