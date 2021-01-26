package com.yoo.lms.repository;

import com.yoo.lms.domain.CounselBoard;
import com.yoo.lms.repository.custom.CounselBoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounselBoardRepository extends JpaRepository<CounselBoard, Long>, CounselBoardRepositoryCustom {
}
