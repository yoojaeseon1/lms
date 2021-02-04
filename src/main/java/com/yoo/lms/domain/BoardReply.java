package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BoardReply {


    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="reply_member_id")
    private Member createdBy;

    @OneToOne(fetch=FetchType.LAZY, mappedBy = "reply")
    private QuestionBoard questionBoard;

    @OneToOne(fetch=FetchType.LAZY, mappedBy = "reply")
    private CounselBoard counselBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_board_id")
    private HomeworkBoard homeworkBoard;

    @OneToMany(mappedBy = "reply")
    private List<CourseMaterial> courseMaterials = new ArrayList<>();

    @Embedded
    private DateValue dateValue;

    public BoardReply(String title, String content, Member createdBy, QuestionBoard questionBoard) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.questionBoard = questionBoard;
    }

    public BoardReply(String title, String content, Member createdBy, HomeworkBoard homeworkBoard) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.homeworkBoard = homeworkBoard;
    }

    public void updateReply(String replyTitle, String replyContent) {
        this.title = replyTitle;
        this.content = replyContent;
    }

}
