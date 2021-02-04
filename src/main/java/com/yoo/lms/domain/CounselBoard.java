package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
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

    @Id @GeneratedValue
    @Column(name="counsel_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_id")
    private BoardReply reply;

    private DateValue dateValue;

    public CounselBoard(String title, String content, Member createdBy) {
        super(title, content);
        this.createdBy = createdBy;
    }


}
