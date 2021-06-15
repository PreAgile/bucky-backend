package dev.buckybackend.service;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.repository.ImageRepository;
import dev.buckybackend.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StudioRepository studioRepository;

    //이미지 업로드
    @Transactional
    public Long upload(Image image, Long studioId) {
        Studio findStudio = studioRepository.findOne(studioId);
        image.setStudio(findStudio);
        imageRepository.save(image);
        return image.getId();
    }

    //전체 이미지 조회
    public List<Image> findImages() {
        return imageRepository.findAll();
    }
}
