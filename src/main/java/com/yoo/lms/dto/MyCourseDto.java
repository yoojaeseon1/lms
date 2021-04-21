package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MyCourseDto {

    private String courseName;
    private Long courseId;

    @QueryProjection
    public MyCourseDto(String courseName, Long courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
    }
}
