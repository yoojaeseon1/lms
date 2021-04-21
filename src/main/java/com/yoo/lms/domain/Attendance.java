package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.AttendanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Attendance {

    public Attendance(Course course, Student student, LocalDateTime checkedDate, AttendanceType attendanceType) {
        this.course = course;
        this.student = student;
        this.checkedDate = checkedDate;
        this.lastModifiedDate = checkedDate;
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

    @Column(updatable = false)
    private LocalDateTime checkedDate;

    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    public void updateAttendanceType(AttendanceType attendanceType, LocalDateTime modifiedDate) {
        this.attendanceType = attendanceType;
        this.lastModifiedDate = modifiedDate;
    }

}
