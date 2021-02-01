package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.ReplyDateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Q")
@NoArgsConstructor
@Getter
public class QuestionBoard extends Board{

    public QuestionBoard(Course course, String title, String content, Member contentCreatedBy) {
        this.course = course;
        this.title = title;
        this.content = content;
        this.contentCreatedBy = contentCreatedBy;
    }

    public QuestionBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member contentCreatedBy;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="reply_member_id")
    private Member replyCreatedBy;

    @Embedded
    private ReplyDateValue replyDateValue;

}
