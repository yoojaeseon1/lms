package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
public class CourseSchedule {

    @Id @GeneratedValue
    @Column(name="course_schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private LocalTime startTime;
    private LocalTime endTime;

    private DayOfWeek courseDay;

    public void addCourse(Course course) {
        course.getCourseSchedules().add(this);
        this.course = course;
    }

}
