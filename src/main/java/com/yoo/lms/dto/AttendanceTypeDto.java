package com.yoo.lms.dto;


import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AttendanceTypeDto {

    private String studentId;
    private Long attendanceId;
    private AttendanceType attendanceType;

}
