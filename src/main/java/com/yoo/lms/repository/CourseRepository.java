package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> , CourseRepositoryCustom {

    @Override
    @EntityGraph(attributePaths={"teacher"})
    Optional<Course> findById(Long id);

//    Page<Course> findByAllCategories(String text, Pageable pageable);

    List<Course> findByNameContaining(@Param("name") String name);

    List<Course> findByPermission(boolean permission);

//    @Query("select c from Course c join c.teacher")
//    @EntityGraph(attributePaths = {"teacher"})
//    Page<Course> findByJoin(Pageable pageable);

}
