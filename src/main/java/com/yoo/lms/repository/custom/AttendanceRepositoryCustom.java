package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.searchCondition.AtSearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepositoryCustom {

    List<AttendanceListDto> searchStudentAttendList(Long courseId);

    long countStudentAttendance(Long courseId, String memberId, AttendanceType attendanceType);

    List<AttendanceListDto> searchCourseAttendList(AtSearchCondition atSearchCondition);

    long countCourseAttendance(Long courseId, LocalDate searchDate, AttendanceType attendanceType);

    List<AttendanceListDto> searchUpdateList(Long courseId, LocalDate checkedDate);

    List<Attendance> searchMyAttend(AtSearchCondition atSearchCondition);

}
