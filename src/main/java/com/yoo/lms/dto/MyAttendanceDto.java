package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * 출석 확인용(학생 개인)
 * 
 */
@Getter @Setter
public class MyAttendanceDto {

    private LocalDate attandanceDate;
    private AttendanceType attendanceType;

    @QueryProjection
    public MyAttendanceDto(LocalDate attandanceDate, AttendanceType attendanceType) {
        this.attandanceDate = attandanceDate;
        this.attendanceType = attendanceType;
    }

}
