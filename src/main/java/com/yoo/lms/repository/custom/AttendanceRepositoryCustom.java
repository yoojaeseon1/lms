package com.yoo.lms.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;

import java.util.List;

public interface AttendanceRepositoryCustom {

    List<AttendanceListDto> findAttendanceList(Long courseId);

    long countAttendance(Long courseId, String memberId, AttendanceType attendanceType);

}
