package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @EntityGraph(attributePaths={"teacher"})
    Optional<Course> findById(Long id);

    List<Course> findByName(String name);

    List<Course> findByPermission(boolean permission);

    @Query("select c from Course c where c.teacher.name=:teacherName")
    List<Course> findByTeacherName(@Param("teacherName") String teacherName);



}
