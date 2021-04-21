package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.InquirylBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QInquiryBoard.*;
import static com.yoo.lms.domain.QMember.member;


public class InquiryBoardRepositoryImpl implements InquirylBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public InquiryBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable) {

        JPAQuery<BoardListDto> contentBeforeWhere = queryFactory
                .select(new QBoardListDto(
                        inquiryBoard.id,
                        inquiryBoard.title,
                        inquiryBoard.createdDate,
                        inquiryBoard.createdBy.name,
                        inquiryBoard.views.size()
                ))
                .from(inquiryBoard)
                .join(inquiryBoard.createdBy, member);

        JPAQuery<BoardListDto> contentToWhere = makeWhereCondition(contentBeforeWhere, condition, isMultipleCriteria);

        List<BoardListDto> boardListDtos = contentToWhere.orderBy(inquiryBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        JPAQuery<BoardListDto> countBeforeWhere = queryFactory
                .select(new QBoardListDto(inquiryBoard.id))
                .from(inquiryBoard);

        JPAQuery<BoardListDto> countToWhere = makeWhereCondition(countBeforeWhere, condition, isMultipleCriteria);

        long totalCount = countToWhere.fetchCount();


        return new PageImpl<>(boardListDtos, pageable, totalCount);
    }


    private BooleanExpression titleContains(String title) {
        return title == null ? null : inquiryBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : inquiryBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression createdByIdContains(String memberId) {
        return memberId == null ? null : inquiryBoard.createdBy.id.containsIgnoreCase(memberId);
    }

    private JPAQuery<BoardListDto> makeWhereCondition(JPAQuery<BoardListDto> toJoin, BoardSearchCondition condition, boolean isMultipleCriteria) {
        JPAQuery<BoardListDto> toWhere = null;

        if(isMultipleCriteria) {
            toWhere = toJoin
                    .where(titleContains(condition.getTitle())
                            .or(contentContains(condition.getContent()))
                            .or(createdByIdContains(condition.getMemberId())));
        } else {
            toWhere = toJoin
                    .where(titleContains(condition.getTitle()),
                            contentContains(condition.getContent()),
                            createdByIdContains(condition.getMemberId()));
        }

        return toWhere;
    }

}
