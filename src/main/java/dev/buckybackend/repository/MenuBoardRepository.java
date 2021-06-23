package dev.buckybackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MenuBoardRepository {
    private final EntityManager em;

    public void deleteByStudioId(Long studioId) {
        em.createQuery("delete from MenuBoard mb where mb.studio.id = :studio_id")
                .setParameter("studio_id", studioId)
                .executeUpdate();
    }
}
