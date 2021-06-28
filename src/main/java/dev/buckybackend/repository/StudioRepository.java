package dev.buckybackend.repository;

import dev.buckybackend.domain.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findByIsDelete(Character isDelete);
    List<Studio> findByName(String name);
    //pagination
    Page<Studio> findByNameContainsIgnoreCaseAndIsDelete(String name, Character isDelete, Pageable pageable);
    //all
    List<Studio> findByNameContainsIgnoreCaseAndIsDelete(String name, Character isDelete);
}
