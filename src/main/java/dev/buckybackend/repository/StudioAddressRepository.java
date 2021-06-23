package dev.buckybackend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class StudioAddressRepository {
    private final EntityManager em;

    public void deleteByStudioId(Long studioId) {
        em.createQuery("delete from StudioAddress sa where sa.studio.id = :studio_id")
                .setParameter("studio_id", studioId)
                .executeUpdate();
    }
}
