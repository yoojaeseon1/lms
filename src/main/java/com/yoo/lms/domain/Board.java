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

    private int viewCount;
    private String title;
    private String content;

}
