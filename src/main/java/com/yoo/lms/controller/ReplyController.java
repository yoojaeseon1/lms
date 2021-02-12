package com.yoo.lms.controller;

import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.service.BoardReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final BoardReplyService boardReplyService;

    @GetMapping("/{courseId}/question/{boardId}/new")
    public String createQuestionReplyForm(Model model){

        model.addAttribute("canUploadFile", false);
        model.addAttribute("formTitle", "질문 답변");

        return "board/boardCreateForm";
    }

    @PostMapping("/{courseId}/question/{boardId}/new")
    public String createQuestionReply(@PathVariable("courseId") Long courseId,
                              @PathVariable("boardId") Long boardId,
                              String title,
                              String content){

        Student student = new Student("studentId100",
                "1234",
                "name",
                "email",
                new Address("1","2","3"),
                LocalDate.now(), MemberType.STUDENT);

        boardReplyService.save(title, content, "studentId1", boardId);

        return "redirect:/";
    }

    @GetMapping("/{courseId}/homework/{boardId}/new")
    public String createHomeworkReplyForm(Model model){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("formTitle", "과제 제출");

        return "board/boardCreateForm";
    }


    @PostMapping("/{courseId}/homework/{boardId}/new")
    public String createHomeworkReply(@PathVariable("courseId") Long courseId,
                                      @PathVariable("boardId") Long boardId,
                                      String title,
                                      String content,
                                      MultipartFile[] files
                                      ) throws IOException {

//        Student student = new Student("studentId100",
//                "1234",
//                "name",
//                12,
//                new Address("1","2","3"),
//                LocalDate.now(), MemberType.STUDENT);`

        boardReplyService.saveWithFile(title, content, boardId, files);

        return "redirect:/";
    }

}
