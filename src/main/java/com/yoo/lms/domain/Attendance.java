package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Attendance {

    public Attendance(Course course, Student student, LocalDate checkDate, AttendanceType attendanceType) {
        this.course = course;
        this.student = student;
        this.checkDate = checkDate;
        this.attendanceType = attendanceType;
    }

    @Id @GeneratedValue
    @Column(name="attendance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Student student;

    private LocalDate checkDate;

    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    public void updateAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

}
