package com.yoo.lms.repository;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.repository.custom.TeacherRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String>, TeacherRepositoryCustom {


}
