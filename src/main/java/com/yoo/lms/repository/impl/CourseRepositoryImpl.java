package com.yoo.lms.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.QCourse;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.yoo.lms.domain.QCourse.*;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CourseRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Course> findByTeacherName(String teacherName, Pageable pageable) {

        QueryResults<Course> results = queryFactory.selectFrom(course)
                .join(course.teacher).fetchJoin()
                .where(course.teacher.name.eq(teacherName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        long total = results.getTotal();

        List<Course> content = results.getResults();

        return new PageImpl<>(content, pageable, total);
    }
}
