package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceCountDto;
import com.yoo.lms.searchCondition.AtSearchCondition;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepositoryCustom {

    List<AttendanceCountDto> findStudentAttendList(Long courseId);

    long countStudentAttendance(Long courseId, String memberId, AttendanceType attendanceType);

    List<AttendanceCountDto> searchCourseAttendList(AtSearchCondition atSearchCondition);

    long countCourseAttendance(Long courseId, LocalDateTime searchDate, AttendanceType attendanceType);

    List<AttendanceCountDto> searchUpdateList(Long courseId, LocalDateTime checkedDate);

    List<Attendance> searchMyAttendances(AtSearchCondition atSearchCondition);

}
