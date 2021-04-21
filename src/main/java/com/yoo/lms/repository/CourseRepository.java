package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.repository.custom.CourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> , CourseRepositoryCustom {

}
