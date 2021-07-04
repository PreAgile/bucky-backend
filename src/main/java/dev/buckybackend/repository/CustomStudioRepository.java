package dev.buckybackend.repository;

import dev.buckybackend.domain.Studio;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomStudioRepository {

    List<Studio> findByFilter(@Param("name") String name,
                              @Param("isDelete") Character isDelete,
                              @Param("hairMakeup") Boolean hairMakeup,
                              @Param("rentClothes") Boolean rentClothes,
                              @Param("tanning") Boolean tanning,
                              @Param("waxing") Boolean waxing,
                              @Param("parking") Boolean parking);
}
