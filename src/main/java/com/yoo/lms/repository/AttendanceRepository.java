package com.yoo.lms.repository;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.repository.custom.AttendanceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceRepositoryCustom {



}
