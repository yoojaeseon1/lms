package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.ReplyDateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class CounselBoard {

    public CounselBoard(String title, String content, Member contentCreatedBy) {
        this.title = title;
        this.content = content;
        this.contentCreatedBy = contentCreatedBy;
    }

    @Id @GeneratedValue
    @Column(name="counsel_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member contentCreatedBy;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_member_id")
    private Member replyCreatedBy;
    private String replyContent;

    private ReplyDateValue replyDateValue;


}
