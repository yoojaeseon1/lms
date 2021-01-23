package com.yoo.lms.repository;

import com.yoo.lms.domain.CourseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long> {
}
