package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.CourseAcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class CourseSearchCondition {

    private String courseName;
    private String teacherName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private CourseAcceptType acceptType;


    public CourseSearchCondition(String courseName, String teacherName, LocalDate startDate, LocalDate endDate, CourseAcceptType acceptType) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.acceptType = acceptType;
    }
}
