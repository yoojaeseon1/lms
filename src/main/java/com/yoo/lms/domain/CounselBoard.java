package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class CounselBoard {

    @Id @GeneratedValue
    @Column(name="counsel_board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couselee_id")
    private Member counselee;
    private LocalDateTime contentCreatedDate;
    private LocalDateTime contentLastModifiedDate;

    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="counseler_id")
    private Member counseler;
    private LocalDateTime replyCreatedDate;
    private LocalDateTime replyLastModifiedDate;

}
