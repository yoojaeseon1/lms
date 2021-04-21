package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("H")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HomeworkBoard extends Board{

    public HomeworkBoard(Course course, String title, String content, Member createdBy) {
        super(title, content, createdBy);
        this.course = course;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;


    /**
     * 과제 하나에 제출 인원당 한 개의 답변이 달리므로 두 개 이상의 답변이 달릴 수 있어야 한다.
     */
    @OneToMany(mappedBy="homeworkBoard")
    private List<BoardReply> replies = new ArrayList<>();

}
