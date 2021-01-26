package com.yoo.lms.repository;

import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.repository.custom.QBoardRepositoryCustom;
import com.yoo.lms.repository.impl.QuestionBoardRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long>, QBoardRepositoryCustom {

}
