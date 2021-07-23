package dev.buckybackend.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import dev.buckybackend.domain.Studio;
import dev.buckybackend.dto.StudioSelectNumDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomStudioRepository {
    List<StudioSelectNumDto> findStudioImageLikeNumByUser(Long userId);
}
