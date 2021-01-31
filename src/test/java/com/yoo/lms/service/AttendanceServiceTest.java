package com.yoo.lms.service;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.repository.AttendanceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AttendanceServiceTest {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    EntityManager em;

//    @Test
//    public void updateAttendance(){
//
//        //given
//
//        //when
//        attendanceService.updateAttendance(90L, AttendanceType.ABSENCE);
//
//        em.flush();
//
//        Attendance findAttendance = attendanceService.findById(90L);
//
//        //then
//
//        assertThat(findAttendance.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
//
//
//    }

}