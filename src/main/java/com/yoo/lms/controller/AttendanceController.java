package com.yoo.lms.controller;

import com.yoo.lms.dto.AttendanceListDto;
import com.yoo.lms.dto.AttendanceTypeDto;
import com.yoo.lms.dto.AttendanceTypeListDto;
import com.yoo.lms.searchCondition.AtSearchCondition;
import com.yoo.lms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/course/{courseId}/attendance/new")
    public String createAttendanceForm(@PathVariable("courseId") Long courseId,
                                       Model model
                                       ){

        List<AttendanceListDto> attendances = attendanceService.searchStudentAttendList(courseId);

        log.info("====================");
        log.info("check null attendance type : " + (attendances.get(0).getAttendanceType() == null));
//        log.info("check string attendance type : " + (AttendanceType.ATTENDANCE.toString().equals("ATTENDANCE")));
        log.info("====================");

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 등록");

        return "attendance/studentList";
    }

    @PostMapping("/course/{courseId}/attendance")
    public String createAttendance(@PathVariable("courseId") Long courseId,
                                   AttendanceTypeListDto attendanceStateListDto
                                    ){

        List<AttendanceTypeDto> states = attendanceStateListDto.getStates();


        log.info("=============");
        log.info("into createAttendance(post)");
        log.info("=============");

//        for (AttendanceStateDto state : states) {
//            log.info(state.getStudentId());
//            log.info(state.getAttendState());
//            log.info("=============");
//        }

        attendanceService.save(courseId, states);

        return "redirect:/course/"+courseId+"/attendance";
    }

    @GetMapping("/course/{courseId}/attendance/update")
    public String updateAttendanceForm(@PathVariable("courseId") Long courseId,
                                       @RequestParam("checkedDate")
                                       @DateTimeFormat(pattern="yyyy-MM-dd")
                                               LocalDate checkedDate,
                                       Model model
                                       ) {

        log.info("checkedDate : " + checkedDate);

        List<AttendanceListDto> attendances = attendanceService.searchUpdateList(courseId, checkedDate);

        log.info("====================");
        log.info("execute updateAttendanceForm");
        log.info("check null attendance type : " + (attendances.get(0).getAttendanceType() == null));
        log.info("====================");

        model.addAttribute("attendances", attendances);
        model.addAttribute("menuTitle", "출석 수정");

        // test용 chekdDate(RequestParam으로 받아야 됨)

        model.addAttribute("checkedDate", LocalDate.now());


        return "attendance/studentList";
    }

//    @PutMapping("{courseId}/attendance")
//    public String updateAttendance(@PathVariable("courseId") Long courseId,
//                                   @RequestParam("checkedDate")
//                                   @DateTimeFormat(pattern="yyyy-MM-dd")
//                                           LocalDate checkedDate,
//                                   AttendanceTypeListDto AttendanceTypeListDto
//                                   ) {
    @PutMapping("/course/{courseId}/attendance")
    public String updateAttendance(AttendanceTypeListDto AttendanceTypeListDto,
                                   @PathVariable("courseId") Long courseId
                                   ) {

        log.info("=================");
        log.info("execute updateAttendance(put)");
        log.info("=================");


//        for (AttendanceStateDto state : states) {
//            log.info("state.attendanceId : " + state.getAttendanceId());
//            log.info("state.attendanceState : " + state.getAttendState());
//            log.info("=============");
//        }

//        List<AttendanceTypeDto> states = attendanceStateListDto.getStates();

        attendanceService.updateAttendance(AttendanceTypeListDto.getStates());


        return "redirect:/course/"+courseId+"/attendance";

    }

    @GetMapping("/course/{courseId}/attendance")
    public String listAttendance(@PathVariable("courseId") Long courseId,
                                 @RequestParam(name = "startDate", required = false)
                                 @DateTimeFormat(pattern="yyyy-MM-dd")
                                         LocalDate startDate,
                                 @RequestParam(name = "endDate", required = false)
                                 @DateTimeFormat(pattern="yyyy-MM-dd")
                                         LocalDate endDate,
                                 Model model
                                 ) {


//        log.info("startDate : " + startDate);
//        log.info("endDate : " + endDate);


        AtSearchCondition atSearchCondition = new AtSearchCondition(courseId, startDate, endDate);

        List<AttendanceListDto> attendanceListDtos = attendanceService.searchCourseAttendList(atSearchCondition);

//        for (AttendanceListDto attendanceListDto : attendanceListDtos) {
//            log.info("getAttendanceId() : " + attendanceListDto.getAttendanceId());
//        }


        model.addAttribute("attendanceListDtos", attendanceListDtos);
//        model.addAttribute("checkedDate", LocalDate.of(2021,2,7));
//        model.addAttribute("checkedDate", attendanceListDtos.get(0).getCheckedDate());

        return "attendance/attendanceList";
//        return "redirect:/";

    }

}
