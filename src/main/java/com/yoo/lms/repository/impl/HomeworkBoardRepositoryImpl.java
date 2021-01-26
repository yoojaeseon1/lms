package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.domain.QHomeworkBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.HBoardRepositoryCumstom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QCourseBoard.courseBoard;
import static com.yoo.lms.domain.QHomeworkBoard.*;
import static com.yoo.lms.domain.QMember.member;

public class HomeworkBoardRepositoryImpl implements HBoardRepositoryCumstom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public HomeworkBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BoardListDto> searchByAllCriteria(String keyword, Pageable pageable) {

        BooleanExpression condition = homeworkBoard
                .title.contains(keyword)
                .or(homeworkBoard.content.contains(keyword))
                .or(homeworkBoard.createdBy.name.contains(keyword));

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByAllCriteria(String keyword) {
        BooleanExpression condition = homeworkBoard
                .title.contains(keyword)
                .or(homeworkBoard.content.contains(keyword))
                .or(homeworkBoard.createdBy.name.contains(keyword));

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByTitle(String title, Pageable pageable) {

        BooleanExpression condition = homeworkBoard
                .title.contains(title);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByTitle(String title) {
        BooleanExpression condition = homeworkBoard
                .title.contains(title);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByContent(String content, Pageable pageable) {
        BooleanExpression condition = homeworkBoard
                .content.contains(content);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByContent(String content) {
        BooleanExpression condition = homeworkBoard
                .content.contains(content);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByWriter(String writer, Pageable pageable) {
        BooleanExpression condition = homeworkBoard
                .createdBy.name.contains(writer);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByWriter(String writer) {
        BooleanExpression condition = homeworkBoard
                .createdBy.name.contains(writer);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {

        return queryFactory
                .select(new QBoardListDto(
                        homeworkBoard.id,
                        homeworkBoard.title,
                        homeworkBoard.dateValue.createdDate,
                        homeworkBoard.createdBy.name,
                        homeworkBoard.viewCount
                ))
                .from(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .where(condition)
                .orderBy(homeworkBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


    }

    @Override
    public long createTotalCountQuery(BooleanExpression condition) {
        return queryFactory
                .selectFrom(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .where(condition)
                .fetchCount();
    }


}
