package com.yoo.lms.controller;

import com.yoo.lms.dto.AttendanceCountDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<AttendanceCountDto> attendances = attendanceService.findStudentAttendList(courseId);

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 등록");

        return "attendance/studentList";
    }

    @PostMapping("/{courseId}/attendance")
    public String createAttendance(@PathVariable Long courseId,
                                   AttendanceTypeListDto attendanceTypeListDto){

        List<AttendanceTypeDto> states = attendanceTypeListDto.getStates();

        LocalDateTime currentTime = LocalDateTime.now();

        attendanceService.save(courseId, states, currentTime);

        return "redirect:/courses/"+courseId+"/attendance";
    }


    @GetMapping("/{courseId}/attendance")
    public String listAttendance(AtSearchCondition atSearchCondition,
                                 Model model) {

        List<AttendanceCountDto> attendanceCountDtos = attendanceService.searchCourseAttendList(atSearchCondition);

        model.addAttribute("attendanceCountDtos", attendanceCountDtos);

        return "attendance/attendanceList";

    }

    @GetMapping("/{courseId}/attendance/update")
    public String updateAttendanceForm(@PathVariable Long courseId,
                                       @RequestParam
                                       @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
                                               LocalDateTime checkedDate,
                                       Model model) {

        List<AttendanceCountDto> attendances = attendanceService.findUpdateList(courseId, checkedDate);

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 수정");
        model.addAttribute("checkedDate", checkedDate);


        return "attendance/studentList";
    }

    @PutMapping("/{courseId}/attendance")
    public String updateAttendance(@PathVariable Long courseId,
                                   AttendanceTypeListDto AttendanceTypeListDto) {


        attendanceService.updateAttendance(AttendanceTypeListDto.getStates());


        return "redirect:/courses/"+courseId+"/attendance";

    }


}


