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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String filename;
    private String directory;

    @CreatedDate
    private LocalDateTime createdDate;

    public CourseMaterial(Board board, String filename, String directory) {
        this.board = board;
        this.filename = filename;
        this.directory = directory;
    }
}
