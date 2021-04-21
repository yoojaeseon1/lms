package com.yoo.lms.repository;

import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
import com.yoo.lms.repository.impl.QuestionBoardRepositoryImpl;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long>, QBoardRepositoryCustom {

    @Query("select q from QuestionBoard q join fetch q.createdBy where q.id =:boardId")
    QuestionBoard findByIdFetchMember(@Param("boardId") Long boardId);



}
