package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepositoryCustom {

    Page<Course> findByTeacherName(String teacherName, Pageable pageable);


}
