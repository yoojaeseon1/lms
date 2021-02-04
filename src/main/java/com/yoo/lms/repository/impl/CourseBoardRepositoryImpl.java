package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.CourseBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;

import java.util.List;

import static com.yoo.lms.domain.QBoardReply.boardReply;
import static com.yoo.lms.domain.QCourseBoard.*;
import static com.yoo.lms.domain.QCourseBoard.courseBoard;
import static com.yoo.lms.domain.QQuestionBoard.*;


public class CourseBoardRepositoryImpl implements CourseBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CourseBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CourseBoard findPostingById(Long boardId) {

        return queryFactory
                .selectFrom(courseBoard)
                .where(courseBoard.id.eq(boardId))
                .fetchOne();

    }

    @Override
    public long deletePostingById(Long boardId) {
        return queryFactory
                .delete(courseBoard)
                .where(courseBoard.id.eq(boardId))
                .execute();
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
                        courseIdEq(condition.getCourseId()),
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
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        createdByIdContains(condition.getMemberId())
                        )
                .fetchCount();

    }

    @Override
    public List<BoardListDto> searchPostingAllCriteria(BoardSearchCondition condition, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
//                        questionBoard.replyDateValue.contentCreatedDate,
                        courseBoard.dateValue.createdDate,
                        courseBoard.createdBy.id,
                        courseBoard.viewCount
                ))
                .from(courseBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle())
                                .or(contentContains(condition.getContent()))
                                .or(createdByIdContains(condition.getMemberId()))
                )
                .orderBy(courseBoard.id.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    @Override
    public long countTotalAllCriteria(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }


        return queryFactory
                .select(new QBoardListDto(
                        courseBoard.id,
                        courseBoard.title,
//                        questionBoard.replyDateValue.contentCreatedDate,
                        courseBoard.dateValue.createdDate,
//                        questionBoard.contentCreatedBy.id,
                        courseBoard.createdBy.id,
                        courseBoard.viewCount
                ))
                .from(courseBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle())
                                .or(contentContains(condition.getContent()))
                                .or(createdByIdContains(condition.getMemberId()))
                )
                .fetchCount();
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




}
