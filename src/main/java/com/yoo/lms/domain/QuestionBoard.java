package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Q")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class QuestionBoard extends Board{

    public QuestionBoard(Course course, String title, String content, Member createdBy) {
        super(title, content, createdBy);
        this.course = course;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "questionBoard")
    private BoardReply reply;

    public void setReply(BoardReply reply) {
        this.reply = reply;
    }
}
