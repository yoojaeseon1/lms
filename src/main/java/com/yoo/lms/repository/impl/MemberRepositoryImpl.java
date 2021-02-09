package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.QMember;
import com.yoo.lms.repository.custom.MemberRepositoryCustom;
import com.yoo.lms.searchCondition.MemberSearchCondition;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import static com.yoo.lms.domain.QMember.*;


public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member searchMember(MemberSearchCondition searchCondition) {
        return queryFactory
                .selectFrom(member)
                .where(
                        idEq(searchCondition.getId()),
                        passwordEq(searchCondition.getPassword()),
                        nameEq(searchCondition.getName()),
                        emailEq(searchCondition.getEmail())
                )
                .fetchOne();
    }

    private BooleanExpression idEq(String id){
        return id == null ? null : member.id.eq(id);
    }

    private BooleanExpression passwordEq(String password) {
        return password == null ? null : member.password.eq(password);
    }

    private BooleanExpression nameEq(String name) {
        return name == null ? null : member.name.eq(name);
    }

    private BooleanExpression emailEq(String email) {
        return email == null ? null : member.email.eq(email);
    }

}
