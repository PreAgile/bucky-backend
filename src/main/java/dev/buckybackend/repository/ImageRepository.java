package dev.buckybackend.repository;

import dev.buckybackend.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByStudio(Studio studio);

    @Query("SELECT i FROM Image i WHERE (:peopleNum is null or i.peopleNum = :peopleNum) and (:sex is null or i.sex = :sex)"
    + " and (:color is null or i.color = :color) and (:outdoor is null or i.outdoor = :outdoor)")
    List<Image> findByPeopleNumAndSexAndColorAndOutdoor(@Param("peopleNum")PeopleNum peopleNum,
                                                         @Param("sex") Sex sex,
                                                         @Param("color") Color color,
                                                         @Param("outdoor") Boolean outdoor);
}
