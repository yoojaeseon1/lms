package com.yoo.lms.repository;


import com.yoo.lms.domain.Attendance;
import com.yoo.lms.dto.AttendanceCountDto;
import com.yoo.lms.searchCondition.AtSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        List<AttendanceCountDto> attendances = attendanceRepository.searchStudentAttendList(1L);

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

    @Test
    public void findCourseAttendance(){

        //given

        long courseId = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        AtSearchCondition atSearchCondition = new AtSearchCondition(1L, startDate, endDate);

        //when
        List<AttendanceCountDto> courseAttendances = attendanceRepository.searchCourseAttendList(atSearchCondition);
        System.out.println(" ============================== ");
        for (AttendanceCountDto attendanceCountDto : courseAttendances) {
            System.out.println("attendanceListDto.getNumAttendance() = " + attendanceCountDto.getNumAttendance());
            System.out.println("attendanceListDto.getNumAbsence() = " + attendanceCountDto.getNumAbsence());
            System.out.println("attendanceListDto.getNumLateness() = " + attendanceCountDto.getNumLateness());
            System.out.println(" ============================== ");
        }

        //then

        assertThat(courseAttendances.size()).isEqualTo(1);
        assertThat(courseAttendances.get(0).getNumLateness()).isEqualTo(11);

    }

    @Test
    public void searchMyAttend(){

        //given
        AtSearchCondition atSearchCondition = new AtSearchCondition(1L, "student10", LocalDate.now(), LocalDate.now());

        //when

        List<Attendance> attendances = attendanceRepository.searchMyAttend(atSearchCondition);

        for (Attendance attendance : attendances) {
            System.out.println("attendance = " + attendance.getAttendanceType());
        }

        //then



    }

}