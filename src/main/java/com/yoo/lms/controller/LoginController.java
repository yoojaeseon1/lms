package com.yoo.lms.controller;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.searchCondition.MemberSearchCondition;
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
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final StudentService studentService;
    private final TeacherService teacherService;


    @GetMapping("/join/joinTypeChoise")
    public String chooseMemberTypeForm(Model model) {

        return "login/joinTypeChoise";
    }

    @GetMapping("/join/{memberType}")
    public String joinForm(Model model,
                           @PathVariable("memberType") String memberType
    ) {

        model.addAttribute("memberType", memberType);

        return "login/joinForm";

    }

    @PostMapping("/join/{memberType}")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody Member member,
                                       @PathVariable("memberType") String memberType) {

        log.info("postNumber : " + member.getAddress().getPostNumber());

        ResponseEntity<String> entity = new ResponseEntity<>("success", HttpStatus.OK);

        if(memberType.equals("student"))
            memberService.joinStduent(new Student(member));
        else
            memberService.joinTeacher(new Teacher(member));

        log.info("insert success============");

        return entity;
    }

    @ResponseBody
    @GetMapping("/join/checkDuplicationId")
    public ResponseEntity<String> checkDuplicationID(String id) {

        log.info("id : " + id);

        boolean isDuplicated = memberService.checkDuplicationID(id);

        ResponseEntity<String> entity = null;

        if(isDuplicated)
            entity = new ResponseEntity<>("dup", HttpStatus.OK);
        else
            entity = new ResponseEntity<>("no dup", HttpStatus.OK);

        return entity;
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model){

        model.addAttribute("loginRetry", false);


        return "login/loginForm";
    }


//    public String login(@RequestParam("id") String id,
//                        @RequestParam("password") String password) {
    @GetMapping("/login")
    public String login(MemberSearchCondition searchCondition,
                        HttpServletRequest request,
                        Model model
                        ) {

        log.info("login - id : " + searchCondition.getId());
        log.info("login - password : " + searchCondition.getPassword());
        log.info("login - name : " + searchCondition.getName());
        log.info("login - email : " + searchCondition.getEmail());

        HttpSession session = request.getSession();
        Optional<MemberType> memberTypeOptional = memberService.searchMemberType(searchCondition);

//        if(memberType == null) {
//
//        } else {
//            session.setAttribute("loginMember", member);
//            return "redirect:/";
//        }

        if(memberTypeOptional.isEmpty()) {
            model.addAttribute("loginRetry", true);
            return "redirect:/loginForm";
        }

        MemberType memberType = memberTypeOptional.get();

        if(memberType == MemberType.STUDENT) {

            log.info("login(student) - id : " + searchCondition.getId());


            Student member = studentService.findById(searchCondition.getId());
            session.setAttribute("loginMember", member);
            session.setAttribute("memberType", memberType);

        } else {

            log.info("login(teacher) - id : " + searchCondition.getId());

            Teacher member = teacherService.findById(searchCondition.getId());
            session.setAttribute("loginMember", member);
            session.setAttribute("memberType", memberType);

        }

        return "redirect:/";

    }

    @GetMapping("/findIDForm")
    public String findIdForm(){


        return "/login/idFindForm";
    }

    @GetMapping("/findID")
    public String findID(MemberSearchCondition searchCondition,
                         Model model) {

        String id = memberService.findID(searchCondition);

        StringBuilder hiddenID = new StringBuilder(id);

        for(int hiddenI = hiddenID.length() / 2; hiddenI < hiddenID.length(); hiddenI++) {
            hiddenID.setCharAt(hiddenI,'*');
        }

//        log.info("findId - id's length == 0: " + (id.length()==0));
//        log.info("findId - id is null : " + (id==null));

        model.addAttribute("hiddenID", hiddenID);
        model.addAttribute("id", id);

        return "/login/idFindResult";
    }

    @GetMapping("/findFullID")
    public String findFullID(MemberSearchCondition searchCondition) {

        memberService.sendEmailFullID(searchCondition);

        return "redirect:/";
    }

    @GetMapping("/findPasswordForm")
    public String findPasswordForm(){

        return "login/passwordFindForm";
    }

    @GetMapping("/findPassword")
    public String findPassword(MemberSearchCondition searchCondition,
                               Model model){

        boolean searchResult = memberService.updateTempPW(searchCondition);

        model.addAttribute("searchResult", searchResult);

        return "login/passwordFindResult";
    }
}
