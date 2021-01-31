package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.CourseAcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CourseSearchCondition {

    private String courseName;
    private String teacherName;
    private LocalDate startDate;
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
