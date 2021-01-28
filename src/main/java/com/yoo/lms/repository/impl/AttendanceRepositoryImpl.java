package com.yoo.lms.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.QAttendanceListDto;
import com.yoo.lms.repository.custom.AttendanceRepositoryCustom;
import com.yoo.lms.searchCondition.AtSearchCondition;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.yoo.lms.domain.QAttendance.*;
import static com.yoo.lms.domain.QCourse.*;
import static com.yoo.lms.domain.QStudent.*;
import static com.yoo.lms.domain.QStudentCourse.*;

@Getter
public class AttendanceRepositoryImpl implements AttendanceRepositoryCustom{


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

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
    public List<AttendanceListDto> searchStudentAttendList(Long courseId) {

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

            long numAttendance = countStudentAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.ATTEANDANCE);

            long numAbsence = countStudentAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.ABSENCE);

            long numLateness = countStudentAttendance(courseId,
                    attendanceListDto.getStudentId(),
                    AttendanceType.LATENESS);

            attendanceListDto.initNumAttendanceState(numAttendance, numAbsence, numLateness);
        }
        
        return attendanceListDtos;
    }



    /**
     * 출석 리스트(수정용)
     * 초기화되는 정보 : 이름 / 생년월일 / 출석 횟수 / 결석 횟수 / 지각 횟수 / 해당일자 출석 상태
     * @param courseId
     * @param checkedDate
     * @return
     */
    @Override
    public List<AttendanceListDto> searchUpdateList(Long courseId, LocalDate checkedDate) {

        return queryFactory
                .select(new QAttendanceListDto(
                        attendance.id,
                        attendance.student.name,
                        attendance.student.birthDate,
                        attendance.attendanceType
                ))
                .from(attendance)
                .join(attendance.student, student)
                .where(
                        attendance.course.id.eq(courseId),
                        attendance.checkedDate.eq(checkedDate)
                )
                .orderBy(attendance.student.name.asc())
                .fetch();
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

    @Override
    public List<Attendance> searchMyAttend(AtSearchCondition atSearchCondition) {
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

    /**
     * 해당 과목 수업한 모든 날짜에 대한 출석 상태(출석 / 결석 / 지각) count
     * @param atSearchCondition(courseId, startDate, endDate)
     * @return
     */
    @Override
    public List<AttendanceListDto> searchCourseAttendList(AtSearchCondition atSearchCondition) {
        List<AttendanceListDto> attendanceListDtos = queryFactory
                .select(new QAttendanceListDto(attendance.checkedDate))
                .from(attendance)
                .where(
                        attendance.course.id.eq(atSearchCondition.getCourseId()),
                        checkedDateGoe(atSearchCondition.getStartDate()),
                        checkedDateLoe(atSearchCondition.getEndDate()))
                .groupBy(attendance.checkedDate)
                .orderBy(attendance.checkedDate.desc())
                .fetch();

        for (AttendanceListDto attendanceListDto : attendanceListDtos) {

            long numAttendance = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceListDto.getCheckedDate(),
                    AttendanceType.ATTEANDANCE);

            long numAbsence = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceListDto.getCheckedDate(),
                    AttendanceType.ABSENCE);

            long numLateness = countCourseAttendance(
                    atSearchCondition.getCourseId(),
                    attendanceListDto.getCheckedDate(),
                    AttendanceType.LATENESS);

            attendanceListDto.initNumAttendanceState(numAttendance, numAbsence, numLateness);
        }

        return attendanceListDtos;
    }

    @Override
    public long countCourseAttendance(Long courseId, LocalDate checkedDate, AttendanceType attendanceType) {

        return queryFactory
                .selectFrom(attendance)
                .where(
                        attendance.course.id.eq(courseId),
                        attendance.checkedDate.eq(checkedDate),
                        attendance.attendanceType.eq(attendanceType)
                )
                .fetchCount();
    }

    private BooleanExpression checkedDateGoe(LocalDate checkedDate) {
        return checkedDate == null ? null : attendance.checkedDate.goe(checkedDate);
    }

    private BooleanExpression checkedDateLoe(LocalDate checkedDate) {
        return checkedDate == null ? null : attendance.checkedDate.loe(checkedDate);
    }


}
