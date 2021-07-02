package dev.buckybackend.repository;

import dev.buckybackend.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomImageRepository {
    Page<Image> findByFilterAndStudio(@Param("peopleNum") PeopleNum[] peopleNum,
                                      @Param("sex") Sex[] sex,
                                      @Param("color") Color[] color,
                                      @Param("outdoor") Boolean outdoor,
                                      @Param("studios") List<Studio> studios,
                                      Pageable pageable);
}
