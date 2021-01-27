package com.yoo.lms.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.domain.QBoard;
import com.yoo.lms.domain.QCourseBoard;
import com.yoo.lms.domain.QMember;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.CourseBoardRepositoryCustom;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.yoo.lms.domain.QBoard.*;
import static com.yoo.lms.domain.QCourseBoard.*;
import static com.yoo.lms.domain.QMember.*;


public class CourseBoardRepositoryImpl implements CourseBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CourseBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BoardListDto> searchAll(int page, int size) {

        BooleanExpression condition = null;
        PageRequest pageRequest = PageRequest.of(page, size);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalAll(int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = null;

        return createTotalCountQuery(null);
    }

    @Override
    public List<BoardListDto> searchByAllCriteria(String keyword, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        BooleanExpression condition = courseBoard.title.contains(keyword)
                .or(courseBoard.content.contains(keyword))
                .or(courseBoard.createdBy.id.contains(keyword));

        return createSearchQuery(condition, pageRequest);

    }

    @Override
    public long countTotalByAllCriteria(String keyword, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if (page == 0)
                return (long) numCurrentPageContent;
            else
                return (long) (page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = courseBoard.title.contains(keyword)
                .or(courseBoard.content.contains(keyword))
                .or(courseBoard.createdBy.id.contains(keyword));

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByTitle(String title, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = courseBoard.title.contains(title);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByTitle(String title, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if (page == 0)
                return (long) numCurrentPageContent;
            else
                return (long) (page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = courseBoard.title.contains(title);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByContent(String content, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = courseBoard.content.contains(content);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByContent(String content, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if (page == 0)
                return (long) numCurrentPageContent;
            else
                return (long) (page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = courseBoard.content.contains(content);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByWriter(String writer, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = courseBoard.createdBy.id.contains(writer);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByWriter(String writer, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if (page == 0)
                return (long) numCurrentPageContent;
            else
                return (long) (page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = courseBoard.createdBy.id.contains(writer);

        return createTotalCountQuery(condition);
    }


    @Override
    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {

        return queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
                        courseBoard.dateValue.createdDate,
                        courseBoard.createdBy.id,
                        courseBoard.viewCount
                ))
                .from(courseBoard)
//                .join(courseBoard.createdBy, member)
                .where(condition)
                .orderBy(courseBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long createTotalCountQuery(BooleanExpression condition) {

        return queryFactory
                .selectFrom(courseBoard)
//                .join(courseBoard.createdBy, member)
                .where(condition)
                .fetchCount();
    }
}
