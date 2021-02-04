package com.yoo.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.domain.QCourseMaterial;
import com.yoo.lms.repository.custom.CourseMaterialRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QCourseMaterial.*;

public class CourseMaterialRepositoryImpl implements CourseMaterialRepositoryCustom {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

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
    public long deleteAllByBoardId(Long boardId) {
        return queryFactory
                .delete(courseMaterial)
                .where(courseMaterial.board.id.eq(boardId))
                .execute();

    }
}
