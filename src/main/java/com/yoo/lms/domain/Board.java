package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
public abstract class Board{

    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @OneToMany(mappedBy = "board")
    private List<CourseMaterial> courseMaterials = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<View> views = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="content_member_id")
    private Member createdBy;

    public Board(String title, String content, Member createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public void updateInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
