package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.domain.QBoardReply;
import com.yoo.lms.domain.enumType.BoardType;
import com.yoo.lms.repository.custom.BoardReplyRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yoo.lms.domain.QBoardReply.*;


public class BoardReplyRepositoryImpl implements BoardReplyRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public BoardReplyRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public BoardReply findByBoardIdAndStudentId(Long boardId, String studentId) {
        return queryFactory
                .selectFrom(boardReply)
                .where(boardReply.homeworkBoard.id.eq(boardId),
                        boardReply.createdBy.id.eq(studentId)
                        )
                .fetchOne();
    }


    @Override
    public BoardReply findQuestionReplyByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(boardReply)
                .where(boardReply.questionBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public BoardReply findInquiryReplyByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(boardReply)
                .where(boardReply.inquiryBoard.id.eq(boardId))
                .fetchOne();
    }

    @Override
    public List<BoardReply> findHomeworkRepliesByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(boardReply)
                .where(boardReply.homeworkBoard.id.eq(boardId))
                .fetch();
    }

    @Override
    public boolean existBoardReply(Long boardId, String memberId, BoardType boardType) {

        Integer fetchFirst = queryFactory
                .selectOne()
                .from(boardReply)
//                .where(boardReply.homeworkBoard.id.eq(boardId),
                .where(boardIdEq(boardId, boardType),
                        boardReply.createdBy.id.eq(memberId))
                .fetchFirst();

        return fetchFirst != null;
    }

    private BooleanExpression boardIdEq(Long boardId, BoardType boardType) {

        switch(boardType) {
            case HOMEWORK:
                return boardReply.homeworkBoard.id.eq(boardId);
            case QUESTION:
                return boardReply.questionBoard.id.eq(boardId);
            case INQUIRY:
                return boardReply.inquiryBoard.id.eq(boardId);
            default:
                return null;

        }

//        if(boardType.equals("homework"))
//            return boardReply.homeworkBoard.id.eq(boardId);
//        else if()

    }
}
