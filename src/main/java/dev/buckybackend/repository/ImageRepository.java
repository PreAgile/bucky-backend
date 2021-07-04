package dev.buckybackend.repository;

import dev.buckybackend.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>, CustomImageRepository {
    List<Image> findByStudio(Studio studio);
    List<Image> findByStudioAndIsDelete(Studio studio, Character isDelete);
}
