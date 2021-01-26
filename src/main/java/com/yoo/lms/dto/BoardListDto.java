package com.yoo.lms.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yoo.lms.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BoardListDto {

    private Long id;
    private String title;
    private LocalDateTime createdDate;
    private String createdBy;
    private int viewCount;

    @QueryProjection
    public BoardListDto(Long id, String title, LocalDateTime createdDate, String createdBy, int viewCount) {
        this.id = id;
        this.title = title;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.viewCount = viewCount;
    }

}
