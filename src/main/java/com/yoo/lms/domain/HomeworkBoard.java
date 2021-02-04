package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.DateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("H")
@Getter
@NoArgsConstructor
public class HomeworkBoard extends Board{

    public HomeworkBoard(Course course, String title, String content, Member createdBy) {
        this.course = course;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    @Id @GeneratedValue
    @Column(name="homeword_board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member createdBy;

    @Embedded
    private DateValue dateValue;

    @OneToMany(mappedBy="homeworkBoard")
    private List<BoardReply> replies = new ArrayList<>();

    public void updateInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
