package com.yoo.lms.controller;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.searchCondition.MemberSearchCondition;
import com.yoo.lms.service.CourseService;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;


    @GetMapping("/join/join-type-choise")
    public String chooseMemberTypeForm(Model model) {

        return "login/joinTypeChoise";
    }

    @GetMapping("/join/{memberType}")
    public String joinForm(@PathVariable String memberType,
                           Model model) {

        model.addAttribute("memberType", memberType);

        return "login/joinForm";

    }

    @PostMapping("/join/{memberType}")
    @ResponseBody
    public ResponseEntity<String> join(@PathVariable String memberType,
                                       @RequestBody Member member) {

        if(memberType.equals("student"))
            memberService.joinStduent(new Student(member));
        else
            memberService.joinTeacher(new Teacher(member));

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/join/check-duplication-id")
    public ResponseEntity<String> checkDuplicationID(String id) {

        boolean isDuplicated = memberService.checkDuplicationID(id);

        ResponseEntity<String> entity = null;

        if(isDuplicated)
            entity = new ResponseEntity<>("dup", HttpStatus.OK);
        else
            entity = new ResponseEntity<>("no dup", HttpStatus.OK);

        return entity;
    }

    @GetMapping("/login-form")
    public String loginForm(Model model){

        model.addAttribute("loginRetry", false);

        return "login/loginForm";
    }


    @GetMapping("/login")
    public String login(MemberSearchCondition searchCondition,
                        HttpSession session,
                        Model model) {

        MemberType memberType = memberService.searchMemberType(searchCondition);

        if(memberType == null) {
            model.addAttribute("loginRetry", true);
            return "/login/loginForm";
        }

        List<CourseListDto> courseListDtos = null;

        if(memberType == MemberType.STUDENT) {

            Student member = studentService.findById(searchCondition.getId());
            courseListDtos = courseService.findCListDtosStudent(member.getId());

            session.setAttribute("loginMember", member);
            session.setAttribute("courseListDtos", courseListDtos);


        } else if (memberType == MemberType.TEACHER) {

            Teacher member = teacherService.findById(searchCondition.getId());
            courseListDtos = courseService.findCListDtosTeacher(member.getId());

            session.setAttribute("loginMember", member);
            session.setAttribute("acceptType", member.getAcceptType());
            session.setAttribute("courseListDtos", courseListDtos);

        } else{

            Member member = memberService.findById(searchCondition.getId());

            session.setAttribute("loginMember", member);
        }

        session.setAttribute("memberType", memberType);
        session.setAttribute("sideBarType", "home");


        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("loginMember");
        session.removeAttribute("memberType");
        session.removeAttribute("acceptType");

        return "redirect:/";
    }

    @GetMapping("/find-id-form")
    public String findIdForm(){


        return "/login/idFindForm";
    }

    /**
     * 일부분만 보여주는 아이디 출력
     * @param searchCondition
     * @param model
     * @return
     */

    @GetMapping("/find-id")
    public String findID(MemberSearchCondition searchCondition,
                         Model model) {

        String id = memberService.findID(searchCondition);

        StringBuilder hiddenID = new StringBuilder(id);

        for(int hiddenI = hiddenID.length() / 2; hiddenI < hiddenID.length(); hiddenI++) {
            hiddenID.setCharAt(hiddenI,'*');
        }

        model.addAttribute("hiddenID", hiddenID);
        model.addAttribute("id", id);

        return "/login/idFindResult";
    }

    /**
     * 가리지 않은 전체 아이디 email 전송
     * @param searchCondition
     * @return
     */

    @GetMapping("/find-full-id")
    public String findFullID(MemberSearchCondition searchCondition) {

        memberService.sendEmailFullID(searchCondition);

        return "redirect:/";
    }

    @GetMapping("/find-password-form")
    public String findPasswordForm(){

        return "login/passwordFindForm";
    }

    @GetMapping("/find-password")
    public String findPassword(MemberSearchCondition searchCondition,
                               Model model){

        boolean searchResult = memberService.updateTempPW(searchCondition);

        model.addAttribute("searchResult", searchResult);

        return "login/passwordFindResult";
    }
}
