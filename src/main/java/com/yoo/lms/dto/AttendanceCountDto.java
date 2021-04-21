package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 강사의 출석 등록 / 수정 용 목록
 */

@Getter
@NoArgsConstructor
public class AttendanceCountDto {

    private Long attendanceId;
    private LocalDateTime checkedDate;
    private String studentId;
    private String studentName;
    private LocalDate birthDate;
    private long numAttendance;
    private long numAbsence;
    private long numLateness;
    private AttendanceType attendanceType;

    /**
     * 출석 등록
     * @param studentId controller 전송
     * @param studentName view 출력
     * @param birthDate view 출력
     */
    @QueryProjection
    public AttendanceCountDto(String studentId, String studentName, LocalDate birthDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDate = birthDate;
    }

    /**
     * 출석 수정(view-> controller로 전송할 때 attendanceId가 필요하므로 추가했음)
     * @param attendanceId view -> controller 전송
     * @param studentName view 출력
     * @param birthDate view 출력
     * @param attendanceType view 출력 + controller 전송
     */
    @QueryProjection
    public AttendanceCountDto(Long attendanceId, String studentName, LocalDate birthDate, AttendanceType attendanceType) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDate = birthDate;
        this.attendanceType = attendanceType;
    }

    @QueryProjection
    public AttendanceCountDto(LocalDateTime checkedDate) {
        this.checkedDate = checkedDate;
    }

    public void initNumAttendanceState(long numAttendance, long numAbsence, long numLateness) {

        this.numAttendance = numAttendance;
        this.numAbsence = numAbsence;
        this.numLateness = numLateness;

    }

}
