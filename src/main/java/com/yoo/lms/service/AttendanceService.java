package com.yoo.lms.service;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.repository.AttendanceRepository;
import com.yoo.lms.searchCondition.AtSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void updateAttendance(Long attendanceId, AttendanceType attendanceType){
        Optional<Attendance> attendanceOptional = attendanceRepository.findById(attendanceId);
        Attendance attendance = null;

        if(attendanceOptional.isPresent())
             attendance = attendanceOptional.get();

        attendance.updateAttendanceType(attendanceType);



    }

    public List<AttendanceListDto> searchStudentAttendList(Long courseId) {
        return attendanceRepository.searchStudentAttendList(courseId);
    }

    public List<AttendanceListDto> searchCourseAttendList(AtSearchCondition condition) {
        return attendanceRepository.searchCourseAttendList(condition);
    }

    public List<AttendanceListDto> searchUpdateList(Long courseId, LocalDate checkedDate){
        return attendanceRepository.searchUpdateList(courseId, checkedDate);
    }

    public List<Attendance> searchMyAttend(AtSearchCondition condition) {
        return attendanceRepository.searchMyAttend(condition);
    }




}
