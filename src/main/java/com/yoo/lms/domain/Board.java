package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Board{

    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @OneToMany(mappedBy = "board")
    private List<CourseMaterial> courseMaterials = new ArrayList<>();

    private String title;
    private String content;



}
