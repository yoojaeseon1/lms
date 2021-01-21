package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class CourseMaterial {

    @Id @GeneratedValue
    @Column(name="course_material_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String filename;
    private String directory;
    private String createdBy;
    private LocalDateTime createdDate;


}
