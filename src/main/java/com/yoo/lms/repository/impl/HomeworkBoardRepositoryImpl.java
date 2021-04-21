package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.HomeworkBoardRepositoryCumstom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QHomeworkBoard.*;
import static com.yoo.lms.domain.QMember.member;

@Slf4j
public class HomeworkBoardRepositoryImpl implements HomeworkBoardRepositoryCumstom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public HomeworkBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable) {

        JPAQuery<BoardListDto> contentBeforeWhere = queryFactory
                .select(new QBoardListDto(
                        homeworkBoard.id,
                        homeworkBoard.title,
                        homeworkBoard.createdDate,
                        homeworkBoard.createdBy.name,
                        homeworkBoard.views.size()
                ))
                .from(homeworkBoard);

        JPAQuery<BoardListDto> contentToWhere = makeWhereCondition(contentBeforeWhere, condition, isMultipleCriteria);

        List<BoardListDto> boardListDtos = contentToWhere
                .orderBy(homeworkBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BoardListDto> countBeforeWhere = queryFactory
                .select(new QBoardListDto(homeworkBoard.id))
                .from(homeworkBoard);

        JPAQuery<BoardListDto> countToWhere = makeWhereCondition(countBeforeWhere, condition, isMultipleCriteria);

        long totalCount = countToWhere.fetchCount();

        return new PageImpl<>(boardListDtos, pageable, totalCount);
    }

    @Override
    public HomeworkBoard findPostingById(Long boardId) {

        return  queryFactory
                .selectFrom(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .fetchJoin()
                .where(homeworkBoard.id.eq(boardId))
                .fetchOne();

    }

    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : homeworkBoard.course.id.eq(courseId);
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : homeworkBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : homeworkBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : homeworkBoard.createdBy.id.containsIgnoreCase(writer);
    }

    private JPAQuery<BoardListDto> makeWhereCondition(JPAQuery<BoardListDto> beforeWhere, BoardSearchCondition condition, boolean isMultipleCriteria) {
        JPAQuery<BoardListDto> toWhere = null;

        if(isMultipleCriteria) {
            toWhere = beforeWhere
                    .where(courseIdEq(condition.getCourseId()),
                            titleContains(condition.getTitle())
                            .or(contentContains(condition.getContent()))
                            .or(createdByIdContains(condition.getMemberId())));
        } else {
            toWhere = beforeWhere
                    .where(courseIdEq(condition.getCourseId()),
                            titleContains(condition.getTitle()),
                            contentContains(condition.getContent()),
                            createdByIdContains(condition.getMemberId()));
        }

        return toWhere;
    }

}
