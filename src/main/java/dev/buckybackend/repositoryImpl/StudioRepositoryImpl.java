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
    public List<Studio> findByFilter(String name, Character isDelete, Boolean hairMakeup, Boolean rentClothes, Boolean tanning, Boolean waxing, Boolean parking, Integer[] minPrice, Integer[] maxPrice) {
        return from(studio)
                .where(containsName(name),
                        eqIsDelete(isDelete),
                        eqHairMakeup(hairMakeup),
                        eqRentClothes(rentClothes),
                        eqTanning(tanning),
                        eqWaxing(waxing),
                        eqParking(parking))
                .where(loeMinPrice(maxPrice))
                .where(goeMaxPrice(minPrice))
                .fetch();
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

    private BooleanBuilder loeMinPrice(Integer[] maxPrice) {
        if (ArrayUtils.isEmpty(maxPrice)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer i : maxPrice) builder.or(studio.min_price.loe(i)); //min_price <= any max_price filters
        return builder;
    }

    private BooleanBuilder goeMaxPrice(Integer[] minPrice) {
        if (ArrayUtils.isEmpty(minPrice)) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer i : minPrice) builder.or(studio.max_price.goe(i)); //max_price >= any min_price filters
        return builder;
    }

    private BooleanExpression containsName(String name) {
        if (!StringUtils.hasLength(name)) {
            return null;
        }
        return studio.name.lower().contains(name.toLowerCase());
    }

    private BooleanExpression eqIsDelete(Character isDelete) {
        if (isDelete == null || Character.isSpaceChar(isDelete) || Character.isWhitespace(isDelete)) {
            return null;
        }
        return studio.isDelete.eq(isDelete);
    }

    private BooleanExpression eqHairMakeup(Boolean hairMakeup) {
        if (hairMakeup == null) {
            return null;
        }
        return studio.option.hairMakeup.eq(hairMakeup);
    }

    private BooleanExpression eqRentClothes(Boolean rentClothes) {
        if (rentClothes == null) {
            return null;
        }
        return studio.option.rentClothes.eq(rentClothes);
    }

    private BooleanExpression eqTanning(Boolean tanning) {
        if (tanning == null) {
            return null;
        }
        return studio.option.tanning.eq(tanning);
    }

    private BooleanExpression eqWaxing(Boolean waxing) {
        if (waxing == null) {
            return null;
        }
        return studio.option.waxing.eq(waxing);
    }

    private BooleanExpression eqParking(Boolean parking) {
        if (parking == null) {
            return null;
        }
        return studio.option.parking.eq(parking);
    }
}
