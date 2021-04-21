package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardReply {


    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="reply_member_id")
    private Member createdBy;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="question_board_id")
    private QuestionBoard questionBoard;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="inquiry_board_id")
    private InquiryBoard inquiryBoard;

    // homework에는 게시물 하나에 여러 답변(과제제출)이 있을 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_board_id")
    private HomeworkBoard homeworkBoard;

    @OneToMany(mappedBy = "reply")
    private List<CourseMaterial> courseMaterials = new ArrayList<>();
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

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

    public BoardReply(String title, String content, Member createdBy, InquiryBoard inquiryBoard) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.inquiryBoard = inquiryBoard;
    }

    public void updateReply(String replyTitle, String replyContent) {
        this.title = replyTitle;
        this.content = replyContent;
    }

}
