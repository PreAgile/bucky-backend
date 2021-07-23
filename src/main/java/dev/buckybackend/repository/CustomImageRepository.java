package dev.buckybackend.repository;

import dev.buckybackend.domain.*;
import dev.buckybackend.dto.ImageListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomImageRepository {
    Page<ImageListDto> findByFilterAndStudio(String name,
                                             Character isDelete,
                                             Boolean hairMakeup,
                                             Boolean rentClothes,
                                             Boolean tanning,
                                             Boolean waxing,
                                             Boolean parking,
                                             Integer[] minPrice,
                                             Integer[] maxPrice,
                                             PeopleNum[] peopleNum,
                                             Sex[] sex,
                                             Color[] color,
                                             Boolean outdoor,
                                             Pageable pageable);
}
