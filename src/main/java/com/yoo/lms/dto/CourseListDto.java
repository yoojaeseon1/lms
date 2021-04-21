package com.yoo.lms.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.domain.enumType.CourseAcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseListDto {

    private Long id;
    private String name;
    private AcceptType acceptType;

    @QueryProjection
    public CourseListDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
