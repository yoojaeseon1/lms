package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AttendanceListDto {

    @QueryProjection
    public AttendanceListDto(String studentId, String studentName, LocalDate birthDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDate = birthDate;

    }

    private String studentId;
    private String studentName;
    private LocalDate birthDate;
    private long numAttendance;
    private long numAbsence;
    private long numLateness;
    private String attendanceState;

    public void initNumAttendanceState(long numAttendance, long numAbsence, long numLateness) {

        this.numAttendance = numAttendance;
        this.numAbsence = numAbsence;
        this.numLateness = numLateness;

    }

}
