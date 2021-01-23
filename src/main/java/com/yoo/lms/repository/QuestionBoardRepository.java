package com.yoo.lms.repository;

import com.yoo.lms.domain.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {
}
