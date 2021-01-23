package com.yoo.lms.repository;

import com.yoo.lms.domain.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
}
