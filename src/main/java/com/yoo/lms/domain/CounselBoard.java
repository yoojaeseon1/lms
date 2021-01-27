package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.ReplyDateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@DiscriminatorValue("Counsel")
@Getter
public class CounselBoard extends Board{

    public CounselBoard(String title, String content, Member contentCreatedBy) {
        super(title, content);
        this.contentCreatedBy = contentCreatedBy;
    }

    @Id @GeneratedValue
    @Column(name="counsel_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member contentCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_member_id")
    private Member replyCreatedBy;
    private String replyContent;

    private ReplyDateValue replyDateValue;


}
