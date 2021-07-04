package dev.buckybackend.repositoryImpl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.ArrayUtils;
import dev.buckybackend.domain.*;;
import static dev.buckybackend.domain.QImage.image;
import dev.buckybackend.repository.CustomImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ImageRepositoryImpl extends QuerydslRepositorySupport implements CustomImageRepository {

    public ImageRepositoryImpl() {
        super(Image.class);
    }

    @Override
    public Page<Image> findByFilterAndStudio(PeopleNum[] peopleNum, Sex[] sex, Color[] color, Boolean outdoor, List<Studio> studios, Pageable pageable) {
        QueryResults<Image> result = from(image)
                .where(inPeopleNum(peopleNum),
                        inSex(sex),
                        inColor(color),
                        eqOutdoor(outdoor),
                        inStudio(studios),
                        image.isDelete.eq('N'))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

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
        if (outdoor == null || outdoor == true) {
            return null;
        }
        return image.outdoor.eq(outdoor);
    }

    private BooleanExpression inStudio(List<Studio> studios) {
        return image.studio.in(studios);
    }
}
