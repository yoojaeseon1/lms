package com.yoo.lms.controller;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.searchCondition.TeacherSearchCondition;
import com.yoo.lms.service.CourseService;
import com.yoo.lms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @GetMapping("/course-apply")
    public String listCourseApply(@RequestParam(required = false) AcceptType acceptType,
                                  @RequestParam(required = false) String teacherName,
                                  Model model){


        List<Course> courses = courseService.findCourseByTeacherAndType(teacherName, acceptType);

        model.addAttribute("courses", courses);


        return "/admin/courseApplications";

    }

    @ResponseBody
    @PutMapping("/course-apply")
    public ResponseEntity<String> updateCourseAcceptType(@RequestBody Map<String, String> dataMap) {

        Long courseId = Long.parseLong(dataMap.get("courseId"));
        String acceptType = dataMap.get("acceptType");

        if(acceptType.equals("ACCEPTED"))
            courseService.permitCourse(courseId);
        else
            courseService.rejectCourse(courseId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/teacher-applications")
    public String listTeacherApplication(@ModelAttribute TeacherSearchCondition searchCondition,
                                         Model model) {

        // id, name이 빈칸으로 넘어가면 빈문자열("")이 되기 때문에 동적 쿼리를 위해 null로 다시 초기화
        // 최초 페이지 이동시에는 null 이므로 null 체크 필요

        if(searchCondition.getId() != null && searchCondition.getId().equals(""))
            searchCondition.setId(null);

        if(searchCondition.getName() != null && searchCondition.getName().equals(""))
            searchCondition.setName(null);

        List<Teacher> teachers = teacherService.searchTeachersBySearchCondition(searchCondition);

        model.addAttribute("teachers", teachers);

        return "/admin/teacherApplications";
    }

    @ResponseBody
    @PutMapping("/teacher-applications")
    public ResponseEntity<String> updateTeacherAcceptType(@RequestBody Map<String, String> dataMap){

        String teacherId = dataMap.get("teacherId");
        String acceptType = dataMap.get("acceptType");


        if(acceptType.equals("ACCEPTED")) {
            teacherService.changeAcceptToAccepted(teacherId);
        } else {
            teacherService.changeAcceptToReject(teacherId);
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
