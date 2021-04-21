package com.yoo.lms.controller;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.MyCourseDto;
import com.yoo.lms.searchCondition.AtSearchCondition;
import com.yoo.lms.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my-page")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final StudentCourseService studentCourseService;
    private final CourseService courseService;
    private final AttendanceService attendanceService;


    @GetMapping("/courses")
    public String listAppliedCourse(@RequestParam(required = false) AcceptType acceptType,
                                    Model model,
                                    HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        List<Course> courses = courseService.findByTeacherIdAndAcceptType(loginMember.getId(), acceptType);

        model.addAttribute("courses", courses);

        return "/teacher/courseEnrolls";
    }


    @GetMapping("/teacher-application")
    public String showTeacherAcceptance() {

        return "/teacher/application";
    }

    @GetMapping("/password-check-form")
    public String checkPasswordForm(){

        return "/myPage/passwordCheckForm";
    }

    @PostMapping("/password-check")
    @ResponseBody
    public String checkPassword(@RequestBody String password,
                                HttpServletRequest request) throws UnsupportedEncodingException {

        String decodedPassword = URLDecoder.decode(password,"utf-8");
        password = decodedPassword.substring(0, decodedPassword.length()-1);

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("loginMember");

        if(loginMember.getPassword().equals(password))
            return "ok";

        return "fail";
    }



    @GetMapping("/my-attendances")
    public String checkMyAttendance(@RequestParam(required = false)
                                    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
                                        LocalDate startDate,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                            LocalDate endDate,
                                    @RequestParam(required = false) Long courseId,
                                    HttpSession session,
                                    Model model) {

            Member member = (Member) session.getAttribute("loginMember");

            if(member.getMemberType() == MemberType.STUDENT){
                member = (Student)member;

                List<MyCourseDto> myCourseDtos = studentCourseService.findMyCourseDto(member.getId());

                model.addAttribute("myCourseDtos", myCourseDtos);


                if(courseId != null) {
                    AtSearchCondition atSearchCondition = new AtSearchCondition(courseId, member.getId(), startDate, endDate);

                    List<Attendance> attendances = attendanceService.searchMyAttendances(atSearchCondition);

                    model.addAttribute("attendances", attendances);

                }
        }

        return "/myPage/myAttendances";
    }

    @PostMapping("/teacher-application")
    public String requestPermitTeacher(HttpSession session) {

        Teacher loginMember = (Teacher) session.getAttribute("loginMember");

        teacherService.changeAcceptToWaiting(loginMember.getId());
        loginMember.changeAcceptToWaiting();
        session.setAttribute("acceptType", loginMember.getAcceptType());


        return "redirect:/teacher-application";
    }

    @GetMapping("/information")
    public String updateInfoForm(HttpServletRequest request,
                                 Model model){

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("loginMember");

        model.addAttribute("member", loginMember);


        return "/myPage/memberInfoUpdateForm";

    }

    @PutMapping("/infomation")
    @ResponseBody
    public ResponseEntity<String> updateInfo(@RequestBody Member member,
                                             HttpServletRequest request){

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("loginMember");

        memberService.updateInfo(loginMember.getId(), member);

        session.removeAttribute("loginMember");

        if(loginMember.getMemberType() == MemberType.STUDENT) {
            session.setAttribute("loginMember", studentService.findById(loginMember.getId()));
        } else {
            session.setAttribute("loginMember", teacherService.findById(loginMember.getId()));
        }

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
