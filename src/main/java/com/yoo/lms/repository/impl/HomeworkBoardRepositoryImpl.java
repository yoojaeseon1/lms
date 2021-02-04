package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.dto.QBoardListDto;
import com.yoo.lms.repository.custom.HBoardRepositoryCumstom;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QBoardReply.boardReply;
import static com.yoo.lms.domain.QHomeworkBoard.*;
import static com.yoo.lms.domain.QMember.member;
import static com.yoo.lms.domain.QQuestionBoard.questionBoard;

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
                .selectFrom(homeworkBoard)
                .join(homeworkBoard.createdBy, member)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()),
                        contentCreatedByIdContains(condition.getMemberId()),
                        memberTypeEq(condition.getMemberType()))
                .fetchCount();
    }


    @Override
    public List<BoardListDto> searchPostingAllCriteria(BoardSearchCondition condition, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return queryFactory
                .select(new QBoardListDto(
                        homeworkBoard.id,
                        homeworkBoard.title,
//                        questionBoard.replyDateValue.contentCreatedDate,
                        homeworkBoard.dateValue.createdDate,
                        homeworkBoard.createdBy.id,
                        homeworkBoard.viewCount
                ))
                .from(homeworkBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle())
                                .or(contentContains(condition.getContent()))
                                .or(createdByIdContains(condition.getMemberId()))
                )
                .orderBy(homeworkBoard.id.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
    }

    @Override
    public long countTotalAllCriteria(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {

        if(numCurrentPageContent < size) {
            if(page == 0)
                return (long)numCurrentPageContent;
            else
                return (long)(page * size) + numCurrentPageContent;
        }


        return queryFactory
                .selectFrom(homeworkBoard)
                .where(
                        courseIdEq(condition.getCourseId()),
                        titleContains(condition.getTitle())
                                .or(contentContains(condition.getContent()))
                                .or(createdByIdContains(condition.getMemberId()))
                )
                .fetchCount();
    }


    @Override
    public HomeworkBoard findPostingById(Long boardId, boolean hasReplies) {

        JPAQuery<HomeworkBoard> jpaQuery = queryFactory
                .selectFrom(homeworkBoard);

        if(hasReplies) {
            jpaQuery.leftJoin(homeworkBoard.replies, boardReply)
                    .fetchJoin();
        }

        return jpaQuery.where(homeworkBoard.id.eq(boardId))
                .fetchOne();

    }

    @Override
    public long deletePostingById(Long boardId) {
        return 0;
    }



    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : homeworkBoard.course.id.eq(courseId);
    }

    private BooleanExpression titleContains(String title) {
        return title == null ? null : homeworkBoard.title.containsIgnoreCase(title);
    }

    private BooleanExpression contentContains(String content) {
        return content == null ? null : homeworkBoard.content.containsIgnoreCase(content);
    }

    private BooleanExpression contentCreatedByIdContains(String memberId) {
        return memberId == null ? null : homeworkBoard.createdBy.id.containsIgnoreCase(memberId);
    }

    private BooleanExpression memberTypeEq(MemberType memberType) {
        return memberType == null ? null : homeworkBoard.createdBy.memberType.eq(memberType);
    }

    private BooleanExpression createdByIdContains(String writer) {
        return writer == null ? null : homeworkBoard.createdBy.id.containsIgnoreCase(writer);
    }

}
