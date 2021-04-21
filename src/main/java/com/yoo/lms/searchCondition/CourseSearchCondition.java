package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.AcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter @Setter
public class CourseSearchCondition {

    private String courseName;
    private String teacherName;
    private Long courseId;
    private String teacherId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private AcceptType acceptType;


    public CourseSearchCondition(String courseName, String teacherName, LocalDate startDate, LocalDate endDate, AcceptType acceptType) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.acceptType = acceptType;
    }

    public CourseSearchCondition(String courseName) {
        this.courseName = courseName;
    }


    public CourseSearchCondition(Long courseId, String teacherId) {
        this.courseId = courseId;
        this.teacherId = teacherId;
    }
}
