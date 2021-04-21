package com.yoo.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.repository.custom.CourseMaterialRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QCourseMaterial.*;

public class CourseMaterialRepositoryImpl implements CourseMaterialRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CourseMaterialRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CourseMaterial> findByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(courseMaterial)
                .where(courseMaterial.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public List<CourseMaterial> findByBoardReplyId(Long boardReplyId) {

        return queryFactory
                .selectFrom(courseMaterial)
                .where(courseMaterial.reply.id.eq(boardReplyId))
                .orderBy(courseMaterial.filename.asc())
                .fetch();

    }

    @Override
    public long deleteAllByBoardId(Long boardId) {
        return queryFactory
                .delete(courseMaterial)
                .where(courseMaterial.board.id.eq(boardId))
                .execute();

    }

    @Override
    public long deleteAllByBoardReplyId(Long boardReplyId) {
        return queryFactory
                .delete(courseMaterial)
                .where(courseMaterial.reply.id.eq(boardReplyId))
                .execute();
    }
}
