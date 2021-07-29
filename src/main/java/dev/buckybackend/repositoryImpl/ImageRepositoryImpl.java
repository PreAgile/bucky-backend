package dev.buckybackend.repositoryImpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.util.ArrayUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.buckybackend.domain.*;;
import static dev.buckybackend.domain.QImage.image;
import static dev.buckybackend.domain.QStudio.studio;

import dev.buckybackend.dto.ImageListDto;
import dev.buckybackend.repository.CustomImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImageRepositoryImpl extends QuerydslRepositorySupport implements CustomImageRepository {

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Image.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ImageListDto> findByFilterAndStudio(String name, Character isDelete,
                                                    Boolean hairMakeup, Boolean rentClothes, Boolean tanning, Boolean waxing, Boolean parking,
                                                    Integer[] minPrice, Integer[] maxPrice,
                                                    PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor,
                                                    Pageable pageable) {
        QueryResults<Tuple> tuples = queryFactory.select(studio.id, studio.name, image.id, image.image_url, image.is_release)
                .from(studio, image)
                .where(containsName(name),          //studio
                        eqStudioIsDelete(isDelete), //studio
                        eqHairMakeup(hairMakeup),   //studio
                        eqRentClothes(rentClothes), //studio
                        eqTanning(tanning),         //studio
                        eqWaxing(waxing),           //studio
                        eqParking(parking),         //studio
                        loeMinPrice(maxPrice),      //studio
                        goeMaxPrice(minPrice),      //studio
                        eqImageIsDelete(isDelete),  //image
                        inPeopleNum(peopleNum),     //image
                        inSex(sex),                 //image
                        inColor(color),             //image
                        eqOutdoor(outdoor))         //image
                .where(image.studio.id.eq(studio.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        getOrderSpecifier(pageable.getSort())
                                .stream().toArray(OrderSpecifier[]::new)
                )
                .fetchResults();

        List<ImageListDto> results = tuples.getResults().stream().map(
                t -> new ImageListDto(
                        t.get(image.id),
                        t.get(image.image_url),
                        t.get(studio.id),
                        t.get(studio.name),
                        t.get(image.is_release)
                )
        ).collect(Collectors.toList());

        return new PageImpl<>(results, pageable, tuples.getTotal());
    }

    /** 스튜디오 검색 필터 관련 **/
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

    private BooleanExpression eqStudioIsDelete(Character isDelete) {
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

    /** 이미지 검색 필터 관련 **/

    private BooleanExpression inPeopleNum(PeopleNum[] peopleNum) {
        if (ArrayUtils.isEmpty(peopleNum)) {
            return null;
        }
        return image.peopleNum.in(peopleNum);
    }

    private BooleanExpression inSex(Sex[] sex) {
        if (ArrayUtils.isEmpty(sex)) {
            return null;
        }
        return image.sex.in(sex);
    }

    private BooleanExpression inColor(Color[] color) {
        if (ArrayUtils.isEmpty(color)) {
            return null;
        }
        return image.color.in(color);
    }

    private BooleanExpression eqOutdoor(Boolean outdoor) {
        if (outdoor == null) {
            return null;
        }
        return image.outdoor.eq(outdoor);
    }

    private BooleanExpression eqImageIsDelete(Character isDelete) {
        if (isDelete == null || Character.isSpaceChar(isDelete) || Character.isWhitespace(isDelete)) {
            return null;
        }
        return image.isDelete.eq(isDelete);
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        // Sort
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Image.class, "image");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return orders;
    }
}
