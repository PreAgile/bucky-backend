package dev.buckybackend.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.dto.StudioSelectNumDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomStudioRepository {

    List<Studio> findByFilter(@Param("name") String name,
                              @Param("isDelete") Character isDelete,
                              @Param("hairMakeup") Boolean hairMakeup,
                              @Param("rentClothes") Boolean rentClothes,
                              @Param("tanning") Boolean tanning,
                              @Param("waxing") Boolean waxing,
                              @Param("parking") Boolean parking,
                              @Param("minPrice") Integer[] minPrice,
                              @Param("maxPrice") Integer[] maxPrice);

    List<StudioSelectNumDto> findStudioImageLikeNumByUser(Long userId);
}
