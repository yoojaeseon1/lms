package com.yoo.lms.repository.custom;

import com.yoo.lms.dto.MyCourseDto;

import java.util.List;

public interface StudentCourseRepositoryCustom {


    List<Long> findCourseIds(String studentId);

    List<MyCourseDto> findMyCourse(String studentId);

    boolean existStudentCourse(Long courseId, String studentId);

}
