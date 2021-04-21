package com.yoo.lms.controller;

import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.AttendanceTypeDto;
import com.yoo.lms.dto.AttendanceTypeListDto;
import com.yoo.lms.searchCondition.AtSearchCondition;
import com.yoo.lms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/{courseId}/attendance/new")
    public String createAttendanceForm(@PathVariable Long courseId,
                                       Model model){

        List<AttendanceListDto> attendances = attendanceService.findStudentAttendList(courseId);

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 등록");

        return "attendance/studentList";
    }

    @PostMapping("/{courseId}/attendance")
    public String createAttendance(@PathVariable Long courseId,
                                   AttendanceTypeListDto attendanceStateListDto){

        List<AttendanceTypeDto> states = attendanceStateListDto.getStates();

        LocalDateTime currentTime = LocalDateTime.now();

        attendanceService.save(courseId, states, currentTime);

        return "redirect:/courses/"+courseId+"/attendance";
    }

    @GetMapping("/{courseId}/attendance/update")
    public String updateAttendanceForm(@PathVariable Long courseId,
                                       @RequestParam
                                       @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
                                               LocalDateTime checkedDate,
                                       Model model) {

        List<AttendanceListDto> attendances = attendanceService.findUpdateList(courseId, checkedDate);

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 수정");

        // test용 chekdDate(RequestParam으로 받아야 됨)

        model.addAttribute("checkedDate", LocalDate.now());


        return "attendance/studentList";
    }

    @PutMapping("/{courseId}/attendance")
    public String updateAttendance(@PathVariable Long courseId,
                                   AttendanceTypeListDto AttendanceTypeListDto) {


        attendanceService.updateAttendance(AttendanceTypeListDto.getStates());


        return "redirect:/courses/"+courseId+"/attendance";

    }

    @GetMapping("/{courseId}/attendance")
    public String listAttendance(AtSearchCondition atSearchCondition,
                                 Model model) {

        List<AttendanceListDto> attendanceListDtos = attendanceService.searchCourseAttendList(atSearchCondition);

        model.addAttribute("attendanceListDtos", attendanceListDtos);

        return "attendance/attendanceList";

    }

}


