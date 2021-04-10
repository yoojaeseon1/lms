package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.CourseAcceptType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.dto.QCourseListDto;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import com.yoo.lms.searchCondition.CourseSearchCondition;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudentCourse.*;
import static com.yoo.lms.domain.QTeacher.*;


public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final StudentCourseRepository studentCourseRepository;

    public CourseRepositoryImpl(EntityManager em, StudentCourseRepository studentCourseRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.studentCourseRepository = studentCourseRepository;
    }


    @Override
//    public List<Course> searchCourseByStudent(CourseSearchCondition condition, boolean canApplicable) {
    public List<Course> searchCourseByStudent(CourseSearchCondition condition, String studentId) {


        List<Long> courseIds = studentCourseRepository.findCourseId(studentId);
//
//        for (Long courseId : courseIds) {
//            System.out.println("courseId = " + courseId);
//        }

        return queryFactory
                .selectFrom(course)
                .join(course.teacher, teacher).fetchJoin()
                .where(
                        studentIdNotIn(courseIds),
                        courseNameContains(condition.getCourseName()),
                        teacherNameContains(condition.getTeacherName()),
                        acceptTypeEq(condition.getAcceptType()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate())
//                        currentNumStudentLtMaxNumStudent(canApplicable)
                )
                .orderBy(course.name.asc())
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
    public String findCourseName(Long courseId, String teacherId) {
        return queryFactory
                .select(course.name)
                .from(course)
                .where(course.id.eq(courseId)
                .and(course.teacher.id.eq(teacherId))
                )
                .fetchOne();
    }

    private BooleanExpression courseNameContains(String courseName) {

        return courseName == null ? null : course.name.containsIgnoreCase(courseName);

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

    private BooleanExpression acceptTypeEq(CourseAcceptType acceptType) {
        return acceptType == null ? null : course.acceptType.eq(acceptType);
    }

    private BooleanExpression studentIdNotIn(List<Long> studentIds) {
        return studentIds.size() == 0 ? null : course.id.notIn(studentIds);
    }

//    private BooleanExpression currentNumStudentLtMaxNumStudent(boolean canApplicable) {
//        return canApplicable ? course.currentNumStudent.lt(course.maxNumStudent) : null;
//    }

}
