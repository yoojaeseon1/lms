package com.yoo.lms.repository;

import com.yoo.lms.domain.Student;
import com.yoo.lms.repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String>, StudentRepositoryCustom {
}
