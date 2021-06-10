package dev.buckybackend.repository;

import dev.buckybackend.domain.Studio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudioRepository {
    private final EntityManager em;

    public void save(Studio studio) {
        em.persist(studio);
    }

    public Studio findOne(Long id) {
        return em.find(Studio.class, id);
    }

    public List<Studio> findAll() {
        return em.createQuery("select s from Studio s", Studio.class)
                .getResultList();
    }

    public List<Studio> findByName(String name) {
        return em.createQuery("select s from Studio s where s.name = :name", Studio.class)
                .setParameter("name", name)
                .getResultList();
    }

}
