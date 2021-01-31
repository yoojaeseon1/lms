package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.CourseAcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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
    private CourseAcceptType acceptType;

    @CreatedDate
    private LocalDate createdDate;
    private LocalDate startDate;
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

    @OneToMany(mappedBy="course")
    private List<CourseSchedule> courseSchedules = new ArrayList<>();


    public Course(String name, Teacher teacher, int maxNumStudent, int currentNumStudent, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.teacher = teacher;
        this.maxNumStudent = maxNumStudent;
        this.currentNumStudent = currentNumStudent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.acceptType = CourseAcceptType.WAITING;
    }

    public void addTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getCourses().add(this);
    }

    public void acceptCourse() {
        this.acceptType = CourseAcceptType.ACCEPTED;
    }

    public void rejectCourse() {
        this.acceptType = CourseAcceptType.REJECTED;
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

}
