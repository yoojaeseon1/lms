package com.yoo.lms.repository;

import com.yoo.lms.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewRepository extends JpaRepository<View, Long> {

    @Modifying
    @Query("delete from View v where v.board.id=:boardId")
    int deleteAllByBoardId(@Param("boardId") Long boardId);

}
