package com.yoo.lms.controller;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.service.MemberService;
import com.yoo.lms.service.StudentService;
import com.yoo.lms.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final StudentService studentService;
    private final TeacherService teacherService;


    @GetMapping("/myPage/passwordCheckForm")
    public String checkPasswordForm(){


        return "myPage/passwordCheckForm";
    }

    @PostMapping("/myPage/passwordCheck")
    @ResponseBody
    public String checkPassword(@RequestBody String password,
                                HttpServletRequest request
                                ) throws UnsupportedEncodingException {

        String decodedPassword = URLDecoder.decode(password,"utf-8");

        password = decodedPassword.substring(0, decodedPassword.length()-1);
//        log.info("checkPassword - decodedPassword : " + decodedPassword);
//        log.info("checkPassword - password : " + password);
//        log.info("checkPassword - password(equals 3333=)" + (password.equals("3333=")));

        HttpSession session = request.getSession();

//        MemberType memberType = (MemberType) session.getAttribute("memberType");

        Member loginMember = (Member)session.getAttribute("loginMember");
        log.info("checkPassword - member.addresss.postNumber : " + loginMember.getAddress().getPostNumber());
        log.info("checkPassword - member.addresss.roadAddress : " + loginMember.getAddress().getRoadAddress());
        log.info("checkPassword - member.addresss.detailAddress : " + loginMember.getAddress().getDetailAddress());

        if(loginMember.getPassword().equals(password))
            return "success";

        return "fail";
    }

    @GetMapping("/myPage/memberInfo")
    public String updateInfoForm(HttpServletRequest request,
                                   Model model){

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("loginMember");

        model.addAttribute("member", loginMember);


        return "/myPage/memberInfoUpdateForm";

    }

    @PutMapping("/myPage/infomation")
    @ResponseBody
    public String updateInfo(@RequestBody Member member,
                             HttpServletRequest request
                             ){

        log.info("updateInfo - password : " + member.getPassword());
        log.info("updateInfo - name : " + member.getName());
        log.info("updateInfo - postNumber : " + member.getAddress().getPostNumber());

        HttpSession session = request.getSession();

        Member loginMember = (Member)session.getAttribute("loginMember");

        memberService.updateInfo(loginMember.getId(), member);

        session.removeAttribute("loginMember");

        if(loginMember.getMemberType() == MemberType.STUDENT) {
            session.setAttribute("loginMember", studentService.findById(loginMember.getId()));
        } else {
            session.setAttribute("loginMember", teacherService.findById(loginMember.getId()));
        }

        return "success";
    }




}
