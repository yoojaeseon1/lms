package com.yoo.lms.repository;

import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.custom.CourseBoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long>, CourseBoardRepositoryCustom {

    

}
