package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Course;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {

    List<Course> searchCourseByStudent(CourseSearchCondition condition, boolean canApplicable);

    List<CourseListDto> findCListDtosByStduent(String studentId);

    List<CourseListDto> findCListDtosByTeacher(String teacherId);

    String findCourseName(Long courseId, String teacherId);

    List<Long> findCourseIds(String studentId);

}
