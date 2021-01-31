package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Course;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {

    List<Course> searchCourse(CourseSearchCondition condition);



}
