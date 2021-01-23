package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CourseSchedule {

    public CourseSchedule(Course course) {
        this.course = course;
    }

    @Id @GeneratedValue
    @Column(name="course_schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String startTime;
    private String endTime;

    public void addCourse(Course course) {
        course.getCourseSchedules().add(this);
        this.course = course;
    }

}
