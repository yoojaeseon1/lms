package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.CounselBoardRepositoryCustom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QCounselBoard.counselBoard;
import static com.yoo.lms.domain.QMember.*;


public class CounselBoardRepositoryImpl implements CounselBoardRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CounselBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {


        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        counselBoard.id,
                        counselBoard.title,
                        counselBoard.dateValue.createdDate,
                        counselBoard.createdBy.id,
                        counselBoard.viewCount
                ))
                .from(counselBoard)
                .join(counselBoard.createdBy, member)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        contentCreatedByIdContains(condition.getMemberId()),
                        memberTypeEq(condition.getMemberType()))
                .orderBy(counselBoard.id.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    @Override
    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }

        return queryFactory
                .select(new QBoardListDto(
                        counselBoard.id,
                        counselBoard.title,
                        counselBoard.dateValue.createdDate,
                        counselBoard.createdBy.id,
                        counselBoard.viewCount
                ))
                .from(counselBoard)
                .join(counselBoard.createdBy, member)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        contentCreatedByIdContains(condition.getMemberId()),
                        memberTypeEq(condition.getMemberType()))
                .fetchCount();
    }


    private BooleanExpression titleContains(String title) {
        return title == null ? null : counselBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : counselBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression contentCreatedByIdContains(String memberId) {
        return memberId == null ? null : counselBoard.createdBy.id.containsIgnoreCase(memberId);
    }

    private BooleanExpression memberTypeEq(MemberType memberType) {
        return memberType == null ? null : counselBoard.createdBy.memberType.eq(memberType);
    }

}
