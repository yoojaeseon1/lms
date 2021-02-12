package com.yoo.lms.controller;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join/memberTypeChoise")
    public String chooseMemberTypeForm(Model model) {

        return "member/memberTypeChoise";
    }

    @GetMapping("/join/{memberType}")
    public String joinForm(Model model,
                           @PathVariable("memberType") String memberType
                           ) {

        model.addAttribute("memberType", memberType);

        return "member/joinForm";

    }

    @PostMapping("/join/{memberType}")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody Member member,
//                               @RequestBody Address address,
//                                       String postNumber,
                               @PathVariable("memberType") String memberType) {

//        log.info("member type : " + member.getMemberType());
//        log.info("member Type is student : " + (member.getMemberType() == MemberType.STUDENT));
//        log.info("member Type is teacher : " + (member.getMemberType() == MemberType.TEACHER));
//        log.info("member type's type : " + (member.getMemberType().getClass()));

//        log.info("id : " + member.getId());
//        log.info("password : " + member.getPassword());
//        log.info("memberType : " + memberType);
//        log.info("birthDate's type : " + member.getBirthDate().getClass());
//        log.info("postNumber : " + address.getPostNumber());
//        log.info("roadAddress : " + address.getRoadAddress());
//        log.info("detailAddress : " + address.getDetailAddress());
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


}
