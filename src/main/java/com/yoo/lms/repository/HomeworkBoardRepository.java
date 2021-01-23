package com.yoo.lms.repository;

import com.yoo.lms.domain.HomeworkBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkBoardRepository extends JpaRepository<HomeworkBoard, Long> {
}
