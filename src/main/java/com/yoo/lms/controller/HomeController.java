package com.yoo.lms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

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
