package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class StudentCourse {


    @Id @GeneratedValue
    @Column(name="student_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    public void enrollCourse(Student student, Course course) {

        this.student = student;
        this.course = course;

        student.getStudentCourses().add(this);
        course.getStudentCourses().add(this);

    }
}
