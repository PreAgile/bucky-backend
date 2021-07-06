package dev.buckybackend.repository;

import dev.buckybackend.domain.Image;
import dev.buckybackend.domain.SelectList;
import dev.buckybackend.domain.User;
import dev.buckybackend.dto.SelectListID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SelectListRepository  {

    private final EntityManager em;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public void createById(Long userId, Long imageId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Image> image = imageRepository.findById(imageId);

        SelectList selectList = new SelectList(user.get(), image.get());
        em.persist(selectList);
    }

    public List<SelectList> findById(Long userId) {
        List resultList = em.createQuery("select s from SelectList s where s.user.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
        for (Object o : resultList) {
            System.out.println("o = " + o);
        }

        return (List<SelectList>)resultList;
    }

    public void deleteById(Long userId, Long imageId) {
        em.createQuery("delete from SelectList s where s.user.id = :userId and s.image.id = :imageId")
                .setParameter("userId", userId)
                .setParameter("imageId", imageId)
                .executeUpdate();
    }

//    em.createQuery("INSERT INTO TestDataEntity (NAME, VALUE) VALUES (:name, :value)");
}
