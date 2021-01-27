package com.yoo.lms.service;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void updateAttendance(Long attendanceId, AttendanceType attendanceType){
        Attendance attendance = attendanceRepository.findById(attendanceId).get();
        attendance.updateAttendanceType(attendanceType);
    }

    public Attendance findById(Long attendanceId) {
        return attendanceRepository.findById(attendanceId).get();
    }

}
