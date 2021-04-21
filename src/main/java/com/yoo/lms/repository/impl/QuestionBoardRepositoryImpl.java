package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QBoardReply.boardReply;
import static com.yoo.lms.domain.QQuestionBoard.*;

public class QuestionBoardRepositoryImpl implements QBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public QuestionBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public QuestionBoard findPostingById(Long boardId) {
        return queryFactory
                .selectFrom(questionBoard)
                .leftJoin(questionBoard.reply, boardReply).fetchJoin()
                .where(questionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable) {

        JPAQuery<BoardListDto> contentBeforeWhere = queryFactory
                .select(new QBoardListDto(
                        questionBoard.id,
                        questionBoard.title,
                        questionBoard.createdDate,
                        questionBoard.createdBy.name,
                        questionBoard.views.size()
                ))
                .from(questionBoard);

        JPAQuery<BoardListDto> contentToWhere = makeWhereCondition(contentBeforeWhere, condition, isMultipleCriteria);

        List<BoardListDto> boardListDtos = contentToWhere
                .orderBy(questionBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<BoardListDto> countBeforeWhere = queryFactory
                .select(new QBoardListDto(questionBoard.id))
                .from(questionBoard);

        JPAQuery<BoardListDto> countToWhere = makeWhereCondition(countBeforeWhere, condition, isMultipleCriteria);

        long totalCount = countToWhere.fetchCount();

        return new PageImpl<>(boardListDtos, pageable, totalCount);
    }

    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : questionBoard.course.id.eq(courseId);
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : questionBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : questionBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : questionBoard.createdBy.id.containsIgnoreCase(writer);
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
