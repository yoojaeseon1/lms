package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("H")
@Getter
public class HomeworkBoard extends Board{

    @Id @GeneratedValue
    @Column(name="homeword_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member createdBy;

    @Embedded
    private DateValue dateValue;



}
