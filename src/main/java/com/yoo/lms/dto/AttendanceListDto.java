package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AttendanceListDto {

    private Long attendanceId;

    // 기존 출석 정보
    private LocalDate checkedDate;

    // 출석체크 목록(등록용)
    private String studentId;
    private String studentName;
    private LocalDate birthDate;

    // 기존 출석정보(해당 날짜 전체 count)
    // 출석체크 목록(해당 학생의 count)
    private long numAttendance;
    private long numAbsence;
    private long numLateness;
    
    // 출석체크 목록(수정용)
    private AttendanceType attendanceType;

    /**
     * 출석 등록
     * @param studentId controller 전송
     * @param studentName view 출력
     * @param birthDate view 출력
     */
    @QueryProjection
    public AttendanceListDto(String studentId, String studentName, LocalDate birthDate) {
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
    public AttendanceListDto(Long attendanceId,String studentName, LocalDate birthDate, AttendanceType attendanceType) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthDate = birthDate;
        this.attendanceType = attendanceType;
    }

    @QueryProjection
    public AttendanceListDto(LocalDate checkedDate) {
        this.checkedDate = checkedDate;
    }

    public void initNumAttendanceState(long numAttendance, long numAbsence, long numLateness) {

        this.numAttendance = numAttendance;
        this.numAbsence = numAbsence;
        this.numLateness = numLateness;

    }

    public String changeTypeToString() {
        return this.getAttendanceType().toString();
    }

}
