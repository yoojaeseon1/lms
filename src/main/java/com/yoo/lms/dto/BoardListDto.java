package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.View;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class BoardListDto {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private String createdBy;
    private int viewCount;
    private List<View> views;


    @QueryProjection
    public BoardListDto(Long id) {
        this.id = id;
    }

    @QueryProjection
    public BoardListDto(Long id, String title, LocalDateTime createdDate, String createdBy, int viewCount) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.viewCount = viewCount;
    }

    @QueryProjection
    public BoardListDto(Long id, String title, LocalDateTime createdDate, String createdBy, List<View> views) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.views = views;
    }
}
