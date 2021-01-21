package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.MemberType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class RealTimeCounselBoard {

    @Id
    @GeneratedValue
    @Column(name="rt_counsel_board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="counseler_id")
    private Member counseler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="counselee_id")
    private Member counselee;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private String content;
    private LocalDateTime createdDate;



}
