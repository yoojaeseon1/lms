package com.yoo.lms.repository;

import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.custom.CourseBoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long>, CourseBoardRepositoryCustom {


    @Query("select c from CourseBoard c join fetch c.createdBy where c.id=:boardId")
    CourseBoard findByIdFetchMember(@Param("boardId") Long boardId);

}
