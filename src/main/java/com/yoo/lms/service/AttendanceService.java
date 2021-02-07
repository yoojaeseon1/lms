package com.yoo.lms.service;

import com.yoo.lms.domain.Attendance;
import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.AttendanceTypeDto;
import com.yoo.lms.repository.AttendanceRepository;
import com.yoo.lms.searchCondition.AtSearchCondition;
import lombok.RequiredArgsConstructor;
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
    private final StudentService studentService;
    private final CourseService courseService;

    @Transactional
    public void save(Long courseId, List<AttendanceTypeDto> attendanceStateDtos) {

        Course course = courseService.findOne(courseId);

        for (AttendanceTypeDto attendanceStateDto : attendanceStateDtos) {
            Student student = studentService.findById(attendanceStateDto.getStudentId());

            Attendance attendance = new Attendance(
                    course,
                    student,
                    LocalDate.now(),
                    AttendanceType.valueOf(attendanceStateDto.getAttendanceType()));

            attendanceRepository.save(attendance);

        }

    }

    /**
     * 출석 리스트(등록용)
     * 초기화되는 정보 : 이름 / 생년월일 / 출석 횟수 / 결석 횟수 / 지각 횟수
     * @param courseId
     * @return
     */
    public List<AttendanceListDto> searchStudentAttendList(Long courseId) {
        return attendanceRepository.searchStudentAttendList(courseId);
    }

    /**
     * 해당 과목 수업한 모든 날짜에 대한 출석 상태(출석 / 결석 / 지각) count
     * @param condition(courseId, startDate, endDate)
     * @return
     */
    public List<AttendanceListDto> searchCourseAttendList(AtSearchCondition condition) {
        return attendanceRepository.searchCourseAttendList(condition);
    }


    /**
     * 출석 리스트(수정용)
     * 초기화되는 정보 : 이름 / 생년월일 / 출석 횟수 / 결석 횟수 / 지각 횟수 / 해당일자 출석 상태
     * @param courseId
     * @param checkedDate
     * @return
     */
    public List<AttendanceListDto> searchUpdateList(Long courseId, LocalDate checkedDate){
        return attendanceRepository.searchUpdateList(courseId, checkedDate);
    }

    public List<Attendance> searchMyAttend(AtSearchCondition condition) {
        return attendanceRepository.searchMyAttend(condition);
    }

    @Transactional
    public void updateAttendance(List<AttendanceTypeDto> attendanceTypeDtos){

        for (AttendanceTypeDto attendanceTypeDto : attendanceTypeDtos) {
            Optional<Attendance> attendanceOptional = attendanceRepository.findById(attendanceTypeDto.getAttendanceId());
            Attendance attendance = null;

            if(attendanceOptional.isPresent())
                attendance = attendanceOptional.get();

            attendance.updateAttendanceType(AttendanceType.valueOf(attendanceTypeDto.getAttendanceType()));

        }
//        attendance.updateAttendanceType(attendanceType);



    }


}
