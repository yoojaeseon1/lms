package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.domain.enumType.CourseAcceptType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepositoryCustom {

    List<Course> searchCourseByStudent(CourseSearchCondition condition, String studentId);

    List<Course> findCourseByTeacherAndType(String teacherName, AcceptType acceptType);

    List<CourseListDto> findCListDtosByStduent(String studentId);

    List<CourseListDto> findCListDtosByTeacher(String teacherId);

//    boolean existCourseName(Long courseId, String teacherId);
    boolean existCourseName(CourseSearchCondition condition);

    List<Long> findCourseIds(String studentId);

    List<Course> findByTeacherIdAndAcceptType(String teacherId, AcceptType acceptType);

    boolean existCourseByTeacherIdAndCourseId(Long courseId, String teacherId);

}
