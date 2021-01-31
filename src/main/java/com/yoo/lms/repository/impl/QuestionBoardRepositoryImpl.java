package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QQuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QHomeworkBoard.homeworkBoard;
import static com.yoo.lms.domain.QMember.member;
import static com.yoo.lms.domain.QQuestionBoard.*;

public class QuestionBoardRepositoryImpl implements QBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public QuestionBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        questionBoard.id,
                        questionBoard.title,
                        questionBoard.replyDateValue.contentCreatedDate,
                        questionBoard.contentCreatedBy.id,
                        questionBoard.viewCount
                ))
                .from(questionBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        createdByIdContains(condition.getMemberId())
                        )
                .orderBy(questionBoard.id.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    @Override
    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }


        return queryFactory
                .select(new QBoardListDto(
                        questionBoard.id,
                        questionBoard.title,
                        questionBoard.replyDateValue.contentCreatedDate,
                        questionBoard.contentCreatedBy.id,
                        questionBoard.viewCount
                ))
                .from(questionBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        createdByIdContains(condition.getMemberId())
                )
                .fetchCount();
    }

    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : questionBoard.course.id.eq(courseId);
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : questionBoard.title.contains(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : questionBoard.content.contains(content);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : questionBoard.contentCreatedBy.id.contains(writer);
    }

}
