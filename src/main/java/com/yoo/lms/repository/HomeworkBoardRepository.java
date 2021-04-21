package com.yoo.lms.repository;

import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.repository.custom.HomeworkBoardRepositoryCumstom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HomeworkBoardRepository extends JpaRepository<HomeworkBoard, Long>, HomeworkBoardRepositoryCumstom {

    @Query("select h.title from HomeworkBoard h where h.id=:boardId")
    String findTitleByBoardId(@Param("boardId") Long boardId);

}
