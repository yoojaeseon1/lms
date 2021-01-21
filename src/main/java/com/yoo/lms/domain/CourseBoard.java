package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Getter
public class CourseBoard extends Board{

    @Id @GeneratedValue
    @Column(name="course_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String content;

    @Embedded
    private DateValue dateValue;

}
