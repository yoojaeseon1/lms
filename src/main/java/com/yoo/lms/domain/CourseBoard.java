package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Getter
@NoArgsConstructor
public class CourseBoard extends Board{

    public CourseBoard(String title, String content, Member createdBy, Course course) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.course = course;
    }

    @Id @GeneratedValue
    @Column(name="course_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member createdBy;

    @Embedded
    private DateValue dateValue;

}
