package dev.buckybackend.repositoryImpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.ArrayUtils;
import dev.buckybackend.domain.Studio;
import static dev.buckybackend.domain.QStudio.studio;
import dev.buckybackend.repository.CustomStudioRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class StudioRepositoryImpl extends QuerydslRepositorySupport implements CustomStudioRepository {

    public StudioRepositoryImpl() {
        super(Studio.class);
    }

    @Override
    public List<Studio> findByFilter(String name, Character isDelete, Boolean hairMakeup, Boolean rentClothes, Boolean tanning, Boolean waxing, Boolean parking, Integer[] minPrice, Integer[] maxPrice) {
        return from(studio)
                .where(containsName(name),
                        eqIsDelete(isDelete),
                        eqHairMakeup(hairMakeup),
                        eqRentClothes(rentClothes),
                        eqTanning(tanning),
                        eqWaxing(waxing))
                .where(loeMinPrice(maxPrice))
                .where(goeMaxPrice(minPrice))
                .fetch();
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
