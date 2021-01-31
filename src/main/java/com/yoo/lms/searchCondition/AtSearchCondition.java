package com.yoo.lms.searchCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AtSearchCondition {

    private Long courseId;
    private String studentId;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * 강사의 출석 조회
     * @param courseId
     * @param startDate
     * @param endDate
     */
    public AtSearchCondition(Long courseId, LocalDate startDate, LocalDate endDate) {
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
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
