package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QQuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
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
    public List<BoardListDto> searchByAllCriteria(String keyword, Pageable pageable) {
        BooleanExpression condition = questionBoard
                .title.contains(keyword)
                .or(questionBoard.content.contains(keyword))
                .or(questionBoard.contentCreatedBy.name.contains(keyword));

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByAllCriteria(String keyword) {
        BooleanExpression condition = questionBoard
                .title.contains(keyword)
                .or(questionBoard.content.contains(keyword))
                .or(questionBoard.contentCreatedBy.name.contains(keyword));

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByTitle(String title, Pageable pageable) {
        BooleanExpression condition = questionBoard.title.contains(title);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByTitle(String title) {
        BooleanExpression condition = questionBoard.title.contains(title);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByContent(String content, Pageable pageable) {
        BooleanExpression condition = questionBoard.content.contains(content);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByContent(String content) {
        BooleanExpression condition = questionBoard.content.contains(content);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByWriter(String writer, Pageable pageable) {
        BooleanExpression condition = questionBoard.contentCreatedBy.name.contains(writer);

        return createSearchQuery(condition, pageable);
    }

    @Override
    public long countTotalByWriter(String writer) {
        BooleanExpression condition = questionBoard.contentCreatedBy.name.contains(writer);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {


        return queryFactory
                .select(new QBoardListDto(
                        questionBoard.id,
                        questionBoard.title,
                        questionBoard.replyDateValue.contentCreatedDate,
                        questionBoard.contentCreatedBy.name,
                        questionBoard.viewCount
                ))
                .from(questionBoard)
                .join(questionBoard.contentCreatedBy, member)
                .where(condition)
                .orderBy(questionBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long createTotalCountQuery(BooleanExpression condition) {

        return queryFactory
                .selectFrom(questionBoard)
                .join(questionBoard.contentCreatedBy, member)
                .where(condition)
                .fetchCount();

    }
}
