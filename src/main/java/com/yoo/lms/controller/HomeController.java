package com.yoo.lms.controller;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TeacherService teacherService;

    @GetMapping("/")
    public String home(HttpServletRequest request){

        Teacher teacherName1 = teacherService.findById("teacherid1");

        HttpSession session = request.getSession();

        session.setAttribute("loginMember", teacherName1);
        session.setAttribute("memberType", teacherName1.getMemberType());


        return "home";
    }

//    @GetMapping("/sample")
//    public String sample(){
//        return "shortcodes";
//    }

}
