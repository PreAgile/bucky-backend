package dev.buckybackend.repository;

import dev.buckybackend.domain.Studio;
import dev.buckybackend.domain.StudioAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioAddressRepository extends JpaRepository<StudioAddress, Long> {
    void deleteAllBy(Studio studio);
}
