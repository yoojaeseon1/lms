package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CourseMaterial {

    @Id @GeneratedValue
    @Column(name="course_material_id")
    private Long id;

    private String filename;
    private String directory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_Reply_id")
    private BoardReply reply;

    @CreatedDate
    private LocalDateTime createdDate;

    public CourseMaterial(Board board, String filename, String directory) {
        this.board = board;
        this.filename = filename;
        this.directory = directory;
    }

    public CourseMaterial(String filename, String directory, BoardReply reply) {
        this.filename = filename;
        this.directory = directory;
        this.reply = reply;
    }

    public void addReply(BoardReply boardReply) {
        boardReply.getCourseMaterials().add(this);
        this.reply = boardReply;
    }
}
