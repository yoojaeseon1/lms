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
    public List<BoardListDto> searchByAllCriteria(String keyword, Pageable pageable) {

        BooleanExpression condition = courseBoard.title.contains(keyword)
                .or(courseBoard.content.contains(keyword))
                .or(courseBoard.createdBy.name.contains(keyword));

        return createSearchQuery(condition, pageable);

    }

    @Override
    public long countTotalByAllCriteria(String keyword) {

        BooleanExpression condition = courseBoard.title.contains(keyword)
                .or(courseBoard.content.contains(keyword))
                .or(courseBoard.createdBy.name.contains(keyword));

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByTitle(String title, Pageable pageable) {

        BooleanExpression condition = courseBoard.title.contains(title);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByTitle(String title) {

        BooleanExpression condition = courseBoard.title.contains(title);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByContent(String content, Pageable pageable) {

        BooleanExpression condition = courseBoard.content.contains(content);
        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByContent(String content) {
        BooleanExpression condition = courseBoard.content.contains(content);
        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByWriter(String writer, Pageable pageable) {
        BooleanExpression condition = courseBoard.createdBy.name.contains(writer);
        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByWriter(String writer) {
        BooleanExpression condition = courseBoard.createdBy.name.contains(writer);
        return createTotalCountQuery(condition);
    }


    @Override
    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {

        return queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
                        courseBoard.dateValue.createdDate,
                        courseBoard.createdBy.name,
                        courseBoard.viewCount
                ))
                .from(courseBoard)
                .join(courseBoard.createdBy, member)
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
                .join(courseBoard.createdBy, member)
                .where(condition)
                .fetchCount();
    }
}
