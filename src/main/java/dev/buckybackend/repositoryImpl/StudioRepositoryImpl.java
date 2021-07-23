package dev.buckybackend.repositoryImpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.ArrayUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.buckybackend.domain.Studio;
import static dev.buckybackend.domain.QStudio.studio;
import static dev.buckybackend.domain.QImage.image;
import static dev.buckybackend.domain.QSelectList.selectList;

import dev.buckybackend.dto.StudioSelectNumDto;
import dev.buckybackend.repository.CustomStudioRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class StudioRepositoryImpl extends QuerydslRepositorySupport implements CustomStudioRepository {

    private final JPAQueryFactory queryFactory;

    public StudioRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Studio.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StudioSelectNumDto> findStudioImageLikeNumByUser(Long userId) {
         List<Tuple> tuples = queryFactory.select(studio.id, studio.name, selectList.count()).from(studio, image, selectList)
                 .where(selectList.image.id.eq(image.id))
                 .where(image.studio.id.eq(studio.id))
                 .where(selectList.user.id.eq(userId))
                 .groupBy(studio.id)
                 .fetch();

         List<StudioSelectNumDto> results = tuples.stream().map(
                 t -> new StudioSelectNumDto(t.get(studio.id), t.get(studio.name), t.get(selectList.count()))
         ).collect(Collectors.toList());
         return results;
    }

}
