package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import com.yoo.lms.domain.valueType.ReplyDateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Q")
@NoArgsConstructor
@Getter
public class QuestionBoard extends Board{

    public QuestionBoard(Course course, String title, String content, Member createdBy) {
        this.course = course;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    private String title;
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_id")
    private BoardReply reply;

    private DateValue dateValue;

    public void updateInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
