package com.yoo.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QStudentCourse;
import com.yoo.lms.repository.custom.StudentCourseRepositoryCustom;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QStudentCourse.*;

public class StudentCourseRepositoryImpl implements StudentCourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public StudentCourseRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findCourseId(String studentId) {

        return queryFactory
                .select(studentCourse.course.id)
                .from(studentCourse)
                .where(studentCourse.student.id.eq(studentId))
                .fetch();

    }
}
