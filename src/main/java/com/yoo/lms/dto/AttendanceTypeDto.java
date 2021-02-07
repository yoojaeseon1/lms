package com.yoo.lms.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AttendanceTypeDto {

    private String studentId;
    private Long attendanceId;
    private String attendanceType;

    public AttendanceTypeDto(String studentId, String attendState) {
        this.studentId = studentId;
        this.attendanceType = attendState;
    }
}
