package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.AcceptType;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Teacher teacher;

    private int maxNumStudent;
    private int currentNumStudent;

    @Enumerated(EnumType.STRING)
    private AcceptType acceptType;

    @CreatedDate
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

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

    public Course() {
        this.currentNumStudent = 0;
        this.acceptType = AcceptType.WAITING;
    }

    public Course(String name, int maxNumStudent, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.maxNumStudent = maxNumStudent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentNumStudent = 0;
        this.acceptType = AcceptType.WAITING;
    }

    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getCourses().add(this);
    }

    public void acceptCourse() {
        this.acceptType = AcceptType.ACCEPTED;
    }

    public void rejectCourse() {
        this.acceptType = AcceptType.REJECTED;
    }

    /**
     * update name, teacher, startDate, endDate
     * @param course
     */
    public void updateInfo(Course course){

        if(!this.name.equals(course.getName()))
            this.name = course.getName();

        if(this.teacher.getId() != course.getTeacher().getId())
            this.teacher = course.getTeacher();

        if(this.maxNumStudent != course.getMaxNumStudent())
            this.maxNumStudent = course.getMaxNumStudent();

        if(!this.startDate.toString().equals(course.getStartDate().toString()))
            this.startDate = course.getStartDate();

        if(!this.endDate.toString().equals(course.getEndDate().toString()))
            this.endDate = course.getEndDate();

    }

    public void addCurStudentNum() {
        this.currentNumStudent++;
    }

}
