package dev.buckybackend.repositoryImpl;

import com.querydsl.core.types.dsl.BooleanExpression;
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
                        eqWaxing(waxing),
                        loeMinPrice(maxPrice),
                        goeMaxPrice(minPrice))
                .fetch();
    }

    private BooleanExpression loeMinPrice(Integer[] maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return studio.min_price.loeAny(maxPrice); //min_price <= maxPrice of filter
    }

    private BooleanExpression goeMaxPrice(Integer[] minPrice) {
        if (minPrice == null || minPrice == 0) {
            return null;
        }
        return studio.max_price.goe(minPrice); //max_price >= minPrice of filter
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
