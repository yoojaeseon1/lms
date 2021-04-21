package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceCountDto;
import com.yoo.lms.dto.QAttendanceCountDto;
import com.yoo.lms.repository.custom.AttendanceRepositoryCustom;
import com.yoo.lms.searchCondition.AtSearchCondition;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.yoo.lms.domain.QAttendance.*;
import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudent.*;
import static com.yoo.lms.domain.QStudentCourse.*;

@Getter
@Slf4j
public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom{


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public AttendanceRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 출석 리스트(등록용)
     * 초기화되는 정보 : 이름 / 생년월일 / 출석 횟수 / 결석 횟수 / 지각 횟수
     * @param courseId
     * @return
     */
    @Override
    public List<AttendanceCountDto> findStudentAttendList(Long courseId) {

        List<AttendanceCountDto> attendanceCountDtos =
                queryFactory
                .select(new QAttendanceCountDto(
                        studentCourse.student.id,
                        studentCourse.student.name,
                        studentCourse.student.birthDate))
                .from(course)
                .join(course.studentCourses, studentCourse)
                .where(course.id.eq(courseId))
                .orderBy(studentCourse.student.name.asc())
                .fetch();

        for (AttendanceCountDto attendanceCountDto : attendanceCountDtos) {

            long numAttendance = countStudentAttendance(courseId,
                    attendanceCountDto.getStudentId(),
                    AttendanceType.ATTENDANCE);

            long numAbsence = countStudentAttendance(courseId,
                    attendanceCountDto.getStudentId(),
                    AttendanceType.ABSENCE);

            long numLateness = countStudentAttendance(courseId,
                    attendanceCountDto.getStudentId(),
                    AttendanceType.LATENESS);

            attendanceCountDto.initNumAttendanceState(numAttendance, numAbsence, numLateness);
        }

        return attendanceCountDtos;
    }





    /**
     * 해당 과목을 듣는 학생의 출석상태 count(타입 별)
     * @param courseId
     * @param studentId
     * @param attendanceType
     * @return
     */
    @Override
    public long countStudentAttendance(Long courseId, String studentId, AttendanceType attendanceType) {

        return queryFactory
                .selectFrom(attendance)
                .where(
                        attendance.course.id.eq(courseId),
                        attendance.student.id.eq(studentId),
                        attendance.attendanceType.eq(attendanceType)
                ).fetchCount();
    }


    /**
     * 해당 과목 수업한 모든 날짜에 대한 출석 상태(출석 / 결석 / 지각) count
     * @param atSearchCondition(courseId, startDate, endDate)
     * @return
     */
    @Override
    public List<AttendanceCountDto> searchCourseAttendList(AtSearchCondition atSearchCondition) {

        List<AttendanceCountDto> attendanceCountDtos =
                queryFactory
                .select(new QAttendanceCountDto(attendance.checkedDate))
                .from(attendance)
                .where(
                        attendance.course.id.eq(atSearchCondition.getCourseId()),
                        checkedDateGoe(atSearchCondition.getStartDate()),
                        checkedDateLoe(atSearchCondition.getEndDate()))
                .groupBy(attendance.checkedDate)
                .orderBy(attendance.checkedDate.desc())
                .fetch();

        for (AttendanceCountDto attendanceCountDto : attendanceCountDtos) {

            long numAttendance = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceCountDto.getCheckedDate(),
                    AttendanceType.ATTENDANCE);

            long numAbsence = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceCountDto.getCheckedDate(),
                    AttendanceType.ABSENCE);

            long numLateness = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceCountDto.getCheckedDate(),
                    AttendanceType.LATENESS);

            attendanceCountDto.initNumAttendanceState(numAttendance, numAbsence, numLateness);
        }

        return attendanceCountDtos;
    }

    @Override
    public long countCourseAttendance(Long courseId, LocalDateTime checkedDate, AttendanceType attendanceType) {

        return queryFactory
                .selectFrom(attendance)
                .where(
                        attendance.course.id.eq(courseId),
                        attendance.checkedDate.eq(checkedDate),
                        attendance.attendanceType.eq(attendanceType)
                )
                .fetchCount();
    }

    /**
     * 출석 리스트(수정용)
     * 초기화되는 정보 : 이름 / 생년월일 / 출석 횟수 / 결석 횟수 / 지각 횟수 / 해당일자 출석 상태
     * @param courseId
     * @param checkedDate
     * @return
     */
    @Override
    public List<AttendanceCountDto> searchUpdateList(Long courseId, LocalDateTime checkedDate) {

        return queryFactory
//                .select(new QAttendanceListDto(
                .select(new QAttendanceCountDto(
                        attendance.id,
                        attendance.student.name,
                        attendance.student.birthDate,
                        attendance.attendanceType
                ))
                .from(attendance)
                .join(attendance.student, student)
                .where(
                        attendance.checkedDate.eq(checkedDate),
                        attendance.course.id.eq(courseId)
                )
                .orderBy(attendance.student.name.asc())
                .fetch();
    }

    /**
     * 본인 출석 검색 용
     * @param atSearchCondition
     * @return
     */

    @Override
    public List<Attendance> searchMyAttendances(AtSearchCondition atSearchCondition) {
        return queryFactory
                .selectFrom(attendance)
                .where(
                        attendance.course.id.eq(atSearchCondition.getCourseId()),
                        attendance.student.id.eq(atSearchCondition.getStudentId()),
                        checkedDateGoe(atSearchCondition.getStartDate()),
                        checkedDateLoe(atSearchCondition.getEndDate())
                )
                .orderBy(attendance.checkedDate.desc())
                .fetch();
    }

    private BooleanExpression checkedDateGoe(LocalDateTime checkedDate) {
        return checkedDate == null ? null : attendance.checkedDate.goe(checkedDate);
    }

    private BooleanExpression checkedDateLoe(LocalDateTime checkedDate) {
        return checkedDate == null ? null : attendance.checkedDate.loe(checkedDate);
    }


}
