package com.yoo.lms.searchCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class AtSearchCondition {

    private Long courseId;
    private String studentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * 강사의 출석 조회
     * @param courseId
     * @param startDate
     * @param endDate
     */
    public AtSearchCondition(Long courseId, LocalDateTime startDate, LocalDateTime endDate) {
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 학생의 본인 과목 출석 조회
     * @param courseId
     * @param studentId
     * @param startDate
     * @param endDate
     */
    public AtSearchCondition(Long courseId, String studentId, LocalDate startDate, LocalDate endDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        if(startDate != null)
            this.startDate = startDate.atTime(0,0,0);
        if(endDate != null)
            this.endDate = endDate.atTime(23,59,59);
    }
}
