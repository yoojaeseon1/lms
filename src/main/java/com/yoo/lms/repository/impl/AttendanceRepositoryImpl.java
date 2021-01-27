package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.QAttendanceListDto;
import com.yoo.lms.repository.custom.AttendanceRepositoryCustom;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.List;

import static com.yoo.lms.domain.QAttendance.*;
import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudentCourse.*;

@Getter
public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom{


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AttendanceRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<AttendanceListDto> findAttendanceList(Long courseId) {

        List<AttendanceListDto> attendanceListDtos =
                queryFactory
                .select(new QAttendanceListDto(
                        studentCourse.student.id,
                        studentCourse.student.name,
                        studentCourse.student.birthDate))
                .from(course)
                .join(course.studentCourses, studentCourse)
                .where(course.id.eq(courseId))
                .orderBy(studentCourse.student.name.asc())
                .fetch();

        for (AttendanceListDto attendanceListDto : attendanceListDtos) {

            long numAttendance = countAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.ATTEANDANCE);

            long numAbsence = countAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.ABSENCE);

            long numLateness = countAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.LATENESS);

            attendanceListDto.initNumAttendanceState(numAttendance, numAbsence, numLateness);
        }


        return attendanceListDtos;
    }

    @Override
    public long countAttendance(Long courseId, String memberId, AttendanceType attendanceType) {

        return queryFactory
                .selectFrom(attendance)
                .where(
                        attendance.course.id.eq(courseId),
                        attendance.student.id.eq(memberId),
                        attendance.attendanceType.eq(attendanceType)
                ).fetchCount();
    }

}
