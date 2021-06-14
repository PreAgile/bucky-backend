package dev.buckybackend.repository;

import dev.buckybackend.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {
    private final EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public List<Image> findAll() {
        return em.createQuery("select i from Image i", Image.class)
                .getResultList();
    }

    //TODO: findByStudioId
}
