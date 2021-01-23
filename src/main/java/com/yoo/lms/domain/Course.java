package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Course {

    public Course(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Teacher teacher;

    private boolean permission;

    @OneToMany(mappedBy="course")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseBoard> courseBoards = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<QuestionBoard> questionBoards = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<HomeworkBoard> homeworkBoards = new ArrayList<>();

    @OneToMany(mappedBy="course")
    private List<CourseSchedule> courseSchedules = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDate startDate;
    private LocalDate endDate;


    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getCourses().add(this);
    }

}
