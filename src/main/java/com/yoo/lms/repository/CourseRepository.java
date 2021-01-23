package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @EntityGraph(attributePaths={"teacher"})
    Optional<Course> findById(Long id);

}
