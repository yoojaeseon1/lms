package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.dto.QCourseListDto;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudentCourse.*;
import static com.yoo.lms.domain.QTeacher.*;


public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public CourseRepositoryImpl(EntityManager em, StudentCourseRepository studentCourseRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.studentCourseRepository = studentCourseRepository;
    }


    @Override
    public List<Course> searchCourseByStudent(CourseSearchCondition condition, String studentId) {

        List<Long> courseIds = new ArrayList<>();

        if(studentId != null)
            courseIds = studentCourseRepository.findCourseIds(studentId);

        return queryFactory
                .selectFrom(course)
                .join(course.teacher, teacher).fetchJoin()
                .where(
                        courseIdNotIn(courseIds),
                        courseNameContains(condition.getCourseName()),
                        teacherNameContains(condition.getTeacherName()),
                        acceptTypeEq(condition.getAcceptType()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate())
                )
                .orderBy(course.name.asc())
                .fetch();
    }

    @Override
    public List<Course> findCourseByTeacherAndType(String teacherName, AcceptType acceptType) {
        return queryFactory
                .selectFrom(course)
                .join(course.teacher, teacher).fetchJoin()
                .where(teacherNameContains(teacherName),
                        acceptTypeEq(acceptType))
                .orderBy(course.acceptType.asc())
                .fetch();
    }

    @Override
    public List<CourseListDto> findCListDtosByStduent(String studentId) {

        List<Long> courseIds = findCourseIds(studentId);

        List<CourseListDto> courseListDtos = queryFactory
                .select(new QCourseListDto(
                        course.id,
                        course.name
                ))
                .from(course)
                .where(course.id.in(courseIds))
                .orderBy(course.name.asc())
                .fetch();

        return courseListDtos;
    }

    @Override
    public List<CourseListDto> findCListDtosByTeacher(String teacherId) {

        return queryFactory
                .select(new QCourseListDto(
                        course.id,
                        course.name
                        ))
                .from(course)
                .where(course.teacher.id.eq(teacherId))
                .orderBy(course.name.asc())
                .fetch();

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
    public boolean existCourseName(CourseSearchCondition condition) {

        Integer fetchFirst = queryFactory
                .selectOne()
                .from(course)
                .where(courseIdEq(condition.getCourseId()),
                        courseTeacherIdEq(condition.getTeacherId()),
                        courseNameEq(condition.getCourseName()))
                .fetchFirst();

        return fetchFirst != null;
    }

    @Override
    public List<Course> findByTeacherIdAndAcceptType(String teacherId, AcceptType acceptType) {
        return queryFactory
                .selectFrom(course)
                .where(acceptTypeEq(acceptType),
                        course.teacher.id.eq(teacherId))
                .orderBy(course.acceptType.asc())
                .fetch();
    }

    @Override
    public boolean existCourseByTeacherIdAndCourseId(Long courseId, String teacherId) {

        Integer fetchFirst = queryFactory
                .selectOne()
                .from(course)
                .where(course.teacher.id.eq(teacherId)
                        .and(course.id.eq(courseId)))
                .fetchFirst();

        return fetchFirst != null;
    }

    private BooleanExpression acceptTypeEq(AcceptType acceptType) {
        return acceptType == null ? null : course.acceptType.eq(acceptType);
    }

    private BooleanExpression courseNameContains(String courseName) {
        return courseName == null ? null : course.name.containsIgnoreCase(courseName);
    }

    private BooleanExpression courseIdEq(Long courseId) {
        return courseId == null ? null : course.id.eq(courseId);
    }

    private BooleanExpression courseNameEq(String courseName) {
        return courseName == null ? null : course.name.eq(courseName);
    }

    private BooleanExpression courseTeacherIdEq(String teacherId) {
        return teacherId == null ? null : course.teacher.id.eq(teacherId);
    }

    private BooleanExpression teacherNameContains(String teacherName) {
        return teacherName == null ? null : course.teacher.name.containsIgnoreCase(teacherName);
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate == null ? null : course.startDate.goe(startDate);
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate == null ? null : course.endDate.loe(endDate);
    }

    private BooleanExpression courseIdNotIn(List<Long> courseIds) {
        return courseIds.size() == 0 ? null : course.id.notIn(courseIds);
    }

}
