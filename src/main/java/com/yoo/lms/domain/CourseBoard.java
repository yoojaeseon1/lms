package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Course")
@Getter
@NoArgsConstructor
public class CourseBoard extends Board{

    public CourseBoard(Course course, String title, String content, Member createdBy) {
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


    public void updateInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
