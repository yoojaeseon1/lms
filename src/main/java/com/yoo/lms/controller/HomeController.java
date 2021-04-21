package com.yoo.lms.controller;

import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
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
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session){


        session.setAttribute("sideBarType", "home");
        session.removeAttribute("courseId");

        return "/home";
    }
}
