package dev.buckybackend.repository;

import dev.buckybackend.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, CustomImageRepository {
    List<Image> findByStudio(Studio studio);
    Page<Image> findByStudioAndIsDelete(Studio studio, Character isDelete, Pageable pageable);
    Page<Image> findByPeopleNumAndSexAndColorAndOutdoorAndIsDeleteAndIdNot(PeopleNum peopleNum,
                                                                 Sex sex,
                                                                 Color color,
                                                                 Boolean outdoor,
                                                                 Character isDelete,
                                                                 Long imageId,
                                                                 Pageable pageable);
}
