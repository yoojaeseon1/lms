package com.yoo.lms.repository;


import com.yoo.lms.dto.AttendanceListDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AttendanceRepositoryTest {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Test
    public void findAttendanceList(){

        //given


        //when
        List<AttendanceListDto> attendances = attendanceRepository.findAttendanceList(1L);

//        for (AttendanceListDto attendance : attendances) {
//            System.out.println("attendance.getStudentName() = " + attendance.getStudentName());
//        }
//
//        System.out.println("attendances.size() = " + attendances.size());


        //then
        assertThat(attendances.get(0).getStudentName()).isEqualTo("name1");
        assertThat(attendances.get(0).getNumAttendance()).isEqualTo(2);


        assertThat(attendances.get(2).getNumLateness()).isEqualTo(1);
        assertThat(attendances.get(1).getNumAttendance()).isEqualTo(2);
        assertThat(attendances.get(1).getNumLateness()).isEqualTo(1);

    }



}