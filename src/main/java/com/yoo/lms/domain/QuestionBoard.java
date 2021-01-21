package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.ReplyDateValue;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Q")
@Getter
public class QuestionBoard extends Board{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String content;

    @Embedded
    private ReplyDateValue replyDateValue;

}
