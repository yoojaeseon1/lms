package com.yoo.lms.controller;

import com.yoo.lms.domain.enumType.AttendanceType;
import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.AttendanceStateDto;
import com.yoo.lms.dto.AttendanceStateListDto;
import com.yoo.lms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("{courseId}/attendance/new")
    public String createAttendanceForm(@PathVariable("courseId") Long courseId,
                                       Model model
                                       ){

        List<AttendanceListDto> attendances = attendanceService.searchStudentAttendList(courseId);

        model.addAttribute("attendances", attendances);

        return "attendance/attendanceCreateForm";
    }

    @PostMapping("{courseId}/attendance/new")
    public String createAttendance(@PathVariable("courseId") Long courseId,
                                   AttendanceStateListDto attendanceStateListDto,
                                   Model model
    ){

        List<AttendanceStateDto> states = attendanceStateListDto.getStates();

        for (AttendanceStateDto state : states) {
            log.info(state.getStudentId());
            log.info(state.getAttendState());
            log.info("=============");
        }

//        AttendanceType attendanceType = AttendanceType.valueOf("ATTENDANCE");

//        log.info("attendance type : " + (attendanceType == AttendanceType.ATTENDANCE));

        attendanceService.save(courseId, states);

        return "redirect:/";
    }



}
