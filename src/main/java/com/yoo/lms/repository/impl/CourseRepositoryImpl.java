package com.yoo.lms.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.QCourse;
import com.yoo.lms.domain.QTeacher;
import com.yoo.lms.domain.enumType.CourseAcceptType;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QTeacher.*;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CourseRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Course> searchCourse(CourseSearchCondition condition) {
        return queryFactory
                .selectFrom(course)
                .join(course.teacher, teacher).fetchJoin()
                .where(
                        courseNameContains(condition.getCourseName()),
                        teacherNameContains(condition.getTeacherName()),
                        acceptTypeEq(condition.getAcceptType()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate())
                )
                .orderBy(course.name.asc())
                .fetch();
    }


    private BooleanExpression courseNameContains(String courseName) {
        return courseName == null ? null : course.name.contains(courseName);
    }

    private BooleanExpression teacherNameContains(String teacherName) {
        return teacherName == null ? null : course.teacher.name.contains(teacherName);
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate == null ? null : course.startDate.goe(startDate);
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate == null ? null : course.endDate.loe(endDate);
    }

    private BooleanExpression acceptTypeEq(CourseAcceptType acceptType) {
        return acceptType == null ? null : course.acceptType.eq(acceptType);
    }

}
