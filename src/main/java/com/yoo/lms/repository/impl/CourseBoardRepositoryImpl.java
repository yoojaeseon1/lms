package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.CourseBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.yoo.lms.domain.QBoardReply.boardReply;
import static com.yoo.lms.domain.QCourseBoard.*;
import static com.yoo.lms.domain.QCourseBoard.courseBoard;
import static com.yoo.lms.domain.QQuestionBoard.*;


public class CourseBoardRepositoryImpl implements CourseBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CourseBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable) {


        JPAQuery<BoardListDto> contentBeforeWhere = queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
                        courseBoard.createdDate,
                        courseBoard.createdBy.name,
                        courseBoard.views.size()
                ))
                .from(courseBoard);

        JPAQuery<BoardListDto> contentToWhere = makeWhereCondition(contentBeforeWhere, condition, isMultipleCriteria);

        List<BoardListDto> boardListDtos = contentToWhere.orderBy(courseBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<BoardListDto> countBeforeWhere = queryFactory
                .select(new QBoardListDto(courseBoard.id))
                .from(courseBoard);

        JPAQuery<BoardListDto> countToWhere = makeWhereCondition(countBeforeWhere, condition, isMultipleCriteria);

        long totalCount = countToWhere.fetchCount();

        return new PageImpl<>(boardListDtos, pageable, totalCount);
    }


    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : courseBoard.course.id.eq(courseId);
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : courseBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : courseBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : courseBoard.createdBy.id.containsIgnoreCase(writer);
    }

    private JPAQuery<BoardListDto> makeWhereCondition(JPAQuery<BoardListDto> toJoin, BoardSearchCondition condition, boolean isMultipleCriteria) {

        JPAQuery<BoardListDto> toWhere = null;

        if(isMultipleCriteria) {
            toWhere = toJoin
                    .where(courseIdEq(condition.getCourseId()),
                            titleContains(condition.getTitle())
                            .or(contentContains(condition.getContent()))
                            .or(createdByIdContains(condition.getMemberId())));
        } else {
            toWhere = toJoin
                    .where(courseIdEq(condition.getCourseId()),
                            titleContains(condition.getTitle()),
                            contentContains(condition.getContent()),
                            createdByIdContains(condition.getMemberId()));
        }

        return toWhere;
    }


}
