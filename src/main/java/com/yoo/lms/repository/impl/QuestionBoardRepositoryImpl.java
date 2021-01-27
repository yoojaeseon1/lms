package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QQuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
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

        BooleanExpression condition = questionBoard
                .title.contains(keyword)
                .or(questionBoard.content.contains(keyword))
                .or(questionBoard.contentCreatedBy.id.contains(keyword));

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByAllCriteria(String keyword, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = questionBoard
                .title.contains(keyword)
                .or(questionBoard.content.contains(keyword))
                .or(questionBoard.contentCreatedBy.id.contains(keyword));

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByTitle(String title, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = questionBoard.title.contains(title);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByTitle(String title, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = questionBoard.title.contains(title);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByContent(String content, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = questionBoard.content.contains(content);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByContent(String content, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = questionBoard.content.contains(content);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> searchByWriter(String writer, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        BooleanExpression condition = questionBoard.contentCreatedBy.id.contains(writer);

        return createSearchQuery(condition, pageRequest);
    }

    @Override
    public long countTotalByWriter(String writer, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        BooleanExpression condition = questionBoard.contentCreatedBy.id.contains(writer);

        return createTotalCountQuery(condition);
    }

    @Override
    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {


        return queryFactory
                .select(new QBoardListDto(
                        questionBoard.id,
                        questionBoard.title,
                        questionBoard.replyDateValue.contentCreatedDate,
                        questionBoard.contentCreatedBy.id,
                        questionBoard.viewCount
                ))
                .from(questionBoard)
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
                .where(condition)
                .fetchCount();

    }
}
