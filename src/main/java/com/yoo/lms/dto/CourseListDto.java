package com.yoo.lms.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CourseListDto {

    private Long id;
    private String name;

    @QueryProjection
    public CourseListDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
