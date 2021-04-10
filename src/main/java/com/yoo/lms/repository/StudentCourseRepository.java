package com.yoo.lms.repository;

import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.repository.custom.StudentCourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long>, StudentCourseRepositoryCustom {

    @Modifying
    @Query("delete from StudentCourse sc where sc.course.id=:courseId and sc.student.id=:studentId")
    int deleteStudentCourse(@Param("courseId") Long courseId, @Param("studentId") String studentId);

}
