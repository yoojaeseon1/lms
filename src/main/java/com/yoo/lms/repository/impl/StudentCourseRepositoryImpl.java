package com.yoo.lms.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.QCourse;
import com.yoo.lms.domain.QStudentCourse;
import com.yoo.lms.dto.MyCourseDto;
import com.yoo.lms.dto.QMyCourseDto;
import com.yoo.lms.repository.custom.StudentCourseRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudentCourse.*;

public class StudentCourseRepositoryImpl implements StudentCourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Autowired
    public StudentCourseRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findCourseIds(String studentId) {

        return queryFactory
                .select(studentCourse.course.id)
                .from(studentCourse)
                .where(studentCourse.student.id.eq(studentId))
                .fetch();

    }

    @Override
    public List<MyCourseDto> findMyCourse(String studentId) {

        return queryFactory
                .select(new QMyCourseDto(studentCourse.course.name, studentCourse.course.id))
                .from(studentCourse)
                .join(studentCourse.course, course)
                .where(studentCourse.student.id.eq(studentId))
                .fetch();

    }

    @Override
    public boolean existStudentCourse(Long courseId, String studentId) {


        Integer fetchFirst = queryFactory
                .selectOne()
                .from(studentCourse)
                .where(studentCourse.student.id.eq(studentId)
                        .and(studentCourse.course.id.eq(courseId))
                ).fetchFirst();


        return fetchFirst != null;
    }
}
