package com.yoo.lms.controller;

import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.service.CourseService;
import com.yoo.lms.service.StudentService;
import com.yoo.lms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/")
    public String home(HttpServletRequest request){

//        Teacher teacherName1 = teacherService.findById("teacherid1");

        HttpSession session = request.getSession();

        Student student1 = studentService.findById("studentid1");

        List<CourseListDto> courseListDtos = courseService.findCourseListDtos(student1.getId());

        for (CourseListDto courseListDto : courseListDtos) {
            log.info(courseListDto.getName());
        }

        session.setAttribute("courseListDtos", courseListDtos);
        session.setAttribute("loginMember", student1);
        session.setAttribute("memberType", student1.getMemberType().toString());

//        log.info("teacherName1.getMemberType().toString() : " + teacherName1.getMemberType().toString());


        return "home";
    }

//    @GetMapping("/sample")
//    public String sample(){
//        return "shortcodes";
//    }

}
