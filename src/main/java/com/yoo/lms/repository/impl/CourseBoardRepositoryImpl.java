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
import com.yoo.lms.searchCondition.BoardSearchCondition;
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
    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
                        courseBoard.dateValue.createdDate,
                        courseBoard.createdBy.id,
                        courseBoard.viewCount
                ))
                .from(courseBoard)
                .where(
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        createdByIdContains(condition.getMemberId())
                        )
                .orderBy(courseBoard.id.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    @Override
    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {

        if (numCurrentPageContent < size) {
            if (page == 0)
                return (long) numCurrentPageContent;
            else
                return (long) (page * size) + numCurrentPageContent;
        }

        return queryFactory
                .selectFrom(courseBoard)
                .where(
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        createdByIdContains(condition.getMemberId())
                        )
                .fetchCount();

    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : courseBoard.title.contains(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : courseBoard.content.contains(content);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : courseBoard.createdBy.id.contains(writer);
    }




}
