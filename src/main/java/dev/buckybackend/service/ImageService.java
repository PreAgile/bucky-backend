package dev.buckybackend.service;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    //이미지 업로드
    @Transactional
    public Long upload(Image image) {
        imageRepository.save(image);
        return image.getId();
    }
}
