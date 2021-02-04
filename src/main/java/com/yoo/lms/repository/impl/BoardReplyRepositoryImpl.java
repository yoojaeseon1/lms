package com.yoo.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.domain.QBoardReply;
import com.yoo.lms.repository.custom.BoardReplyRepositoryCustom;

import javax.persistence.EntityManager;

import static com.yoo.lms.domain.QBoardReply.*;


public class BoardReplyRepositoryImpl implements BoardReplyRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BoardReplyRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public BoardReply findByIdAndMember(Long boardId, String studentId) {
        return queryFactory
                .selectFrom(boardReply)
                .where(boardReply.homeworkBoard.id.eq(boardId),
                        boardReply.homeworkBoard.createdBy.id.eq(studentId)
                        )
                .fetchOne();
    }

}
