package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class View {

    @Id @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Board board;

    @CreatedDate
    LocalDateTime createdDate;

    public View(Board board) {
        this.board = board;
    }

    public void addViewCount(Board board) {
        board.getViews().add(this);
        this.board = board;
    }
}
