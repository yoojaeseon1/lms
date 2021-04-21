package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QTeacher;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.repository.custom.TeacherRepositoryCustom;
import com.yoo.lms.searchCondition.TeacherSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QTeacher.*;

@Slf4j
public class TeacherRepositoryImpl implements TeacherRepositoryCustom {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    @Autowired
    public TeacherRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Teacher> searchTeachersBySearchCondition(TeacherSearchCondition searchCondition) {
        return queryFactory
                .selectFrom(teacher)
                .where(nameEq(searchCondition.getName()),
                        idEq(searchCondition.getId()),
                        acceptTypeEq(searchCondition.getAcceptType()))
                .orderBy(teacher.acceptType.asc())
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : teacher.name.eq(name);
    }

    private BooleanExpression idEq(String id) {
        return id == null ? null : teacher.id.eq(id);
    }

    private BooleanExpression acceptTypeEq(AcceptType acceptType) {
        return acceptType == null ? null : teacher.acceptType.eq(acceptType);
    }
}
