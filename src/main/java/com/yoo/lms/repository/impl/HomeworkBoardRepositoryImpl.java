package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.HBoardRepositoryCumstom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QHomeworkBoard.*;
import static com.yoo.lms.domain.QMember.member;

public class HomeworkBoardRepositoryImpl implements HBoardRepositoryCumstom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public HomeworkBoardRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        homeworkBoard.id,
                        homeworkBoard.title,
                        homeworkBoard.dateValue.createdDate,
                        homeworkBoard.createdBy.id,
                        homeworkBoard.viewCount
                ))
                .from(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        contentCreatedByIdContains(condition.getMemberId()),
                        memberTypeEq(condition.getMemberType()))
                .orderBy(homeworkBoard.id.desc())
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
                        homeworkBoard.id,
                        homeworkBoard.title,
                        homeworkBoard.dateValue.createdDate,
                        homeworkBoard.createdBy.id,
                        homeworkBoard.viewCount
                ))
                .from(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        contentCreatedByIdContains(condition.getMemberId()),
                        memberTypeEq(condition.getMemberType()))
                .fetchCount();
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : homeworkBoard.title.contains(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : homeworkBoard.content.contains(content);
    }

    private BooleanExpression contentCreatedByIdContains(String memberId) {
        return memberId == null ? null : homeworkBoard.createdBy.id.contains(memberId);
    }

    private BooleanExpression memberTypeEq(MemberType memberType) {
        return memberType == null ? null : homeworkBoard.createdBy.memberType.eq(memberType);
    }

//    @Override
//    public List<BoardListDto> searchAll(int page, int size) {
//
//        BooleanExpression condition = null;
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        return createSearchQuery(condition, pageRequest);
//    }
//
//    @Override
//    public long countTotalAll(int page, int size, int numCurrentPageContent) {
//
//        if(numCurrentPageContent < size) {
//            if(page == 0)
//                return (long)numCurrentPageContent;
//            else
//                return (long)(page * size) + numCurrentPageContent;
//        }
//
//        BooleanExpression condition = null;
//
//        return createTotalCountQuery(null);
//    }
//
//    @Override
//    public List<BoardListDto> searchByAllCriteria(String keyword, int page, int size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        BooleanExpression condition = homeworkBoard
//                .title.contains(keyword)
//                .or(homeworkBoard.content.contains(keyword))
//                .or(homeworkBoard.createdBy.id.contains(keyword));
//
//        return createSearchQuery(condition, pageRequest);
//    }
//
//    @Override
//    public long countTotalByAllCriteria(String keyword, int page, int size, int numCurrentPageContent) {
//
//        if(numCurrentPageContent < size) {
//            if(page == 0)
//                return (long)numCurrentPageContent;
//            else
//                return (long)(page * size) + numCurrentPageContent;
//        }
//
//        BooleanExpression condition = homeworkBoard
//                .title.contains(keyword)
//                .or(homeworkBoard.content.contains(keyword))
//                .or(homeworkBoard.createdBy.id.contains(keyword));
//
//        return createTotalCountQuery(condition);
//    }
//
//    @Override
//    public List<BoardListDto> searchByTitle(String title, int page, int size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        BooleanExpression condition = homeworkBoard
//                .title.contains(title);
//
//        return createSearchQuery(condition, pageRequest);
//    }
//
//    @Override
//    public long countTotalByTitle(String title, int page, int size, int numCurrentPageContent) {
//
//        if(numCurrentPageContent < size) {
//            if(page == 0)
//                return (long)numCurrentPageContent;
//            else
//                return (long)(page * size) + numCurrentPageContent;
//        }
//
//        BooleanExpression condition = homeworkBoard
//                .title.contains(title);
//
//        return createTotalCountQuery(condition);
//    }
//
//    @Override
//    public List<BoardListDto> searchByContent(String content, int page, int size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        BooleanExpression condition = homeworkBoard
//                .content.contains(content);
//
//        return createSearchQuery(condition, pageRequest);
//    }
//
//    @Override
//    public long countTotalByContent(String content, int page, int size, int numCurrentPageContent) {
//
//        if(numCurrentPageContent < size) {
//            if(page == 0)
//                return (long)numCurrentPageContent;
//            else
//                return (long)(page * size) + numCurrentPageContent;
//        }
//
//        BooleanExpression condition = homeworkBoard
//                .content.contains(content);
//
//        return createTotalCountQuery(condition);
//    }
//
//    @Override
//    public List<BoardListDto> searchByWriter(String writer, int page, int size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        BooleanExpression condition = homeworkBoard
//                .createdBy.id.contains(writer);
//
//        return createSearchQuery(condition, pageRequest);
//    }
//
//    @Override
//    public long countTotalByWriter(String writer, int page, int size, int numCurrentPageContent) {
//
//        if(numCurrentPageContent < size) {
//            if(page == 0)
//                return (long)numCurrentPageContent;
//            else
//                return (long)(page * size) + numCurrentPageContent;
//        }
//
//        BooleanExpression condition = homeworkBoard
//                .createdBy.id.contains(writer);
//
//        return createTotalCountQuery(condition);
//    }
//
//    @Override
//    public List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable) {
//
//        return queryFactory
//                .select(new QBoardListDto(
//                        homeworkBoard.id,
//                        homeworkBoard.title,
//                        homeworkBoard.dateValue.createdDate,
//                        homeworkBoard.createdBy.id,
//                        homeworkBoard.viewCount
//                ))
//                .from(homeworkBoard)
////                .join(homeworkBoard.createdBy, member)
//                .where(condition)
//                .orderBy(homeworkBoard.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//
//    }
//
//    @Override
//    public long createTotalCountQuery(BooleanExpression condition) {
//        return queryFactory
//                .selectFrom(homeworkBoard)
////                .join(homeworkBoard.createdBy, member)
//                .where(condition)
//                .fetchCount();
//    }




}
