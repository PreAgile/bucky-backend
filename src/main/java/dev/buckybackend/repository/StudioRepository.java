package dev.buckybackend.repository;

import dev.buckybackend.domain.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    List<Studio> findByName(String name);
    Page<Studio> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
