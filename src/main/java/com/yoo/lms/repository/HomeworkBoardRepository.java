package com.yoo.lms.repository;

import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.repository.custom.HBoardRepositoryCumstom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkBoardRepository extends JpaRepository<HomeworkBoard, Long>, HBoardRepositoryCumstom {

}
