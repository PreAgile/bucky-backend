package dev.buckybackend.repository;

import dev.buckybackend.domain.Option;
import dev.buckybackend.domain.Studio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {


    List<Studio> findByIsDelete(Character isDelete);
    List<Studio> findByName(String name);
    //pagination
    Page<Studio> findByNameContainsIgnoreCaseAndIsDelete(String name, Character isDelete, Pageable pageable);

    //all
    @Query("SELECT s FROM Studio s WHERE"
            + " (:name is null or lower(s.name) like lower(concat('%', :name, '%')))"
            + " and s.isDelete = :isDelete"
            + " and (:hairMakeup is null or s.option.hairMakeup = :hairMakeup)"
            + " and (:rentClothes is null or s.option.rentClothes = :rentClothes)"
            + " and (:tanning is null or s.option.tanning = :tanning)"
            + " and (:waxing is null or s.option.waxing = :waxing)"
            + " and (:parking is null or s.option.parking = :parking)")
    List<Studio> findByNameContainsIgnoreCaseAndIsDeleteAndOption(@Param("name") String name,
                                                                  @Param("isDelete") Character isDelete,
                                                                  @Param("hairMakeup") Boolean hairMakeup,
                                                                  @Param("rentClothes") Boolean rentClothes,
                                                                  @Param("tanning") Boolean tanning,
                                                                  @Param("waxing") Boolean waxing,
                                                                  @Param("parking") Boolean parking);
}
