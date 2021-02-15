package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.QStudent;
import com.yoo.lms.domain.Student;
import com.yoo.lms.repository.custom.StudentRepositoryCustom;
import com.yoo.lms.searchCondition.MemberSearchCondition;

import javax.persistence.EntityManager;

import static com.yoo.lms.domain.QMember.member;
import static com.yoo.lms.domain.QStudent.*;

public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public StudentRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Student searchStudent(MemberSearchCondition searchCondition) {
        return queryFactory
                .selectFrom(student)
                .where(
                        idEq(searchCondition.getId()),
                        passwordEq(searchCondition.getPassword()),
                        nameEq(searchCondition.getName()),
                        emailEq(searchCondition.getEmail())
                )
                .fetchOne();
    }

    private BooleanExpression idEq(String id){
        return id == null ? null : student.id.eq(id);
    }

    private BooleanExpression passwordEq(String password) {
        return password == null ? null : student.password.eq(password);
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : student.name.eq(name);
    }

    private BooleanExpression emailEq(String email) {
        return email == null ? null : student.email.eq(email);
    }
}
