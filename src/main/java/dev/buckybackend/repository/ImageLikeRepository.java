package dev.buckybackend.repository;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.ImageLike;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
@RequiredArgsConstructor
public class ImageLikeRepository {

    private final EntityManager em;

    @Autowired
    private ImageRepository imageRepository;

    public ImageLike getImageLikeNum(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        Image getImage = image.get();
        if(getImage.getImageLike() == null) {
            ImageLike imageLike = new ImageLike();
            imageLike.setImage(getImage);
            getImage.setImageLike(new ImageLike(getImage,0));
            em.persist(getImage);
            ImageLike like = em.find(ImageLike.class, imageId);
            return like;
        } else {
            return image.get().getImageLike();
        }
    }

    @Transactional
    public void initImageLikeNum(Long imageId) {
        ImageLike imageLike = new ImageLike();
        Optional<Image> image = imageRepository.findById(imageId);
        imageLike.setImage(image.get());
        image.get().setImageLike(imageLike);
        imageRepository.save(image.get());
        em.persist(imageLike);
    }

    @Transactional
    public void plusImageLikeNum(Long imageId) {
        AtomicReference<Integer> likeNum = new AtomicReference<>();
        Optional<ImageLike> imageLike = Optional.ofNullable(getImageLikeNum(imageId));
        imageLike.ifPresentOrElse((il) -> {
            likeNum.set(il.getLike_num() + 1);
        }, () -> {
            likeNum.set(0);
        });
        em.createQuery("update ImageLike i set i.like_num = :likeNum")
                .setParameter("likeNum", likeNum.get())
                .executeUpdate();
    }

    @Transactional
    public void minusImageLikeNum(Long imageId) {
        ImageLike imageLike = getImageLikeNum(imageId);
        Integer likeNum = imageLike.getLike_num() - 1;
        em.createQuery("update ImageLike i set i.like_num = :likeNum")
                .setParameter("likeNum", likeNum)
                .executeUpdate();
    }

}
