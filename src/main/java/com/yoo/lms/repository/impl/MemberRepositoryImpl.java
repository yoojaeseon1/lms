package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.QMember;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.repository.custom.MemberRepositoryCustom;
import com.yoo.lms.searchCondition.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.yoo.lms.domain.QMember.*;


public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
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

//    @Override
//    public Optional<MemberType> searchMemberType(MemberSearchCondition searchCondition) {
//        return Optional.ofNullable(queryFactory
//                .select(member.memberType)
//                .from(member)
//                .where(
//                        idEq(searchCondition.getId()),
//                        passwordEq(searchCondition.getPassword()),
//                        nameEq(searchCondition.getName()),
//                        emailEq(searchCondition.getEmail())
//                )
//                .fetchOne()
//        );
//    }

    @Override
    public MemberType searchMemberType(MemberSearchCondition searchCondition) {
        return queryFactory
                .select(member.memberType)
                .from(member)
                .where(
                        idEq(searchCondition.getId()),
                        passwordEq(searchCondition.getPassword()),
                        nameEq(searchCondition.getName()),
                        emailEq(searchCondition.getEmail())
                )
                .fetchOne();

    }

    @Override
    public boolean isExistId(String id) {

        Integer fetchFirst = queryFactory
                .selectOne()
                .from(member)
                .where(member.id.eq(id))
                .fetchFirst();

        return fetchFirst != null;
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
