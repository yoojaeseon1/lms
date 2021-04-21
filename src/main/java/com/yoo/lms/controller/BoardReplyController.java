package com.yoo.lms.controller;

import com.yoo.lms.domain.*;
import com.yoo.lms.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardReplyController {

    private final BoardReplyService boardReplyService;
    private final QuestionBoardService questionBoardService;
    private final CourseMaterialService courseMaterialService;
    private final HomeworkBoardService homeworkBoardService;
    private final InquiryBoardService inquiryBoardService;

    @GetMapping("/courses/{courseId}/question-board/{boardId}/new")
    public String createQuestionReplyForm(Model model){

        model.addAttribute("canUploadFile", false);
        model.addAttribute("formTitle", "질문 답변");

        return "board/boardCreateForm";
    }


    /**
     *
     * 파일 첨부 불가능
     *
     * @param courseId
     * @param boardId
     * @param title
     * @param content
     * @param session
     * @return
     */

    @PostMapping("/courses/{courseId}/question-board/{boardId}/new")
    public String createQuestionReply(@PathVariable Long boardId,
                                      @PathVariable Long courseId,
                                      String title,
                                      String content,
                                      HttpSession session){

        Teacher member = (Teacher) session.getAttribute("loginMember");

        boardReplyService.saveQuestionReply(title, content, member.getId(), boardId);

        return "redirect:/courses/"+courseId+"/question-board/"+boardId;
    }

    @GetMapping("/courses/{courseId}/question-board/{boardId}/{boardReplyId}/new")
    public String updateQuestionReplyForm(@PathVariable Long boardReplyId,
                                         Model model){

        BoardReply findBoardReply = boardReplyService.findById(boardReplyId);

        model.addAttribute("posting", findBoardReply);
        model.addAttribute("menuTitle", "문의 답변 수정");
        model.addAttribute("canUploadFile", false);

        return "/board/boardUpdateForm";
    }


    @PutMapping("/courses/{courseId}/question-board/{boardId}/{boardReplyId}")
    public String updateQuestionReply(@PathVariable Long courseId,
                                      @PathVariable Long boardId,
                                      @PathVariable Long boardReplyId,
                                      @RequestParam String title,
                                      @RequestParam String content,
                                      HttpSession session) throws IOException{

        Member loginMember = (Member) session.getAttribute("loginMember");

        boardReplyService.updateBoardReply(boardReplyId, loginMember.getId(), title, content, null);

        return "redirect:/courses/"+ courseId + "/question-board/" + boardId;
    }


    @ResponseBody
    @DeleteMapping("/courses/{courseId}/question-board/{boardId}/{boardReplyId}")
    public ResponseEntity<String> deleteQuestionReply(@PathVariable Long boardReplyId) {

        boardReplyService.deleteBoardReply(boardReplyId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }



    @GetMapping("/courses/{courseId}/homework-board/{boardId}/new")
    public String createHomeworkReplyForm(Model model){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("formTitle", "과제 제출");

        return "board/boardCreateForm";
    }

    /**
     * 파일 첨부 가능
     *
     * @param courseId
     * @param boardId
     * @param title
     * @param content
     * @param files
     * @param session
     * @return
     * @throws IOException
     */
    @PostMapping("/courses/{courseId}/homework-board/{boardId}/new")
    public String createHomeworkReply(@PathVariable Long courseId,
                                      @PathVariable Long boardId,
                                      String title,
                                      String content,
                                      MultipartFile[] files,
                                      HttpSession session) throws IOException {

        Member loginMember = (Member) session.getAttribute("loginMember");
        boardReplyService.saveWithFile(title, content, loginMember.getId(), boardId, files);

        return "redirect:/courses/"+courseId+"/homework-board/"+boardId;
    }

    @GetMapping("/courses/{courseId}/homework-board/{boardId}/submits")
    public String listHomeworkSubmit(@PathVariable Long boardId,
                                      Model model) {

        List<BoardReply> submits = boardReplyService.findHomeworkRepliesByBoardId(boardId);
        String title = homeworkBoardService.findTitleByBoardId(boardId);

        model.addAttribute("submits", submits);
        model.addAttribute("title", title);

        return "/homework/submits";
    }

    @GetMapping("/courses/{courseId}/homework-board/{boardId}/submits/{boardReplyId}")
    public String showHomeworkSubmit(@PathVariable Long boardReplyId,
                                     Model model){

        BoardReply boardSubmit = boardReplyService.findById(boardReplyId);

        List<CourseMaterial> submitMaterials = null;
        if(boardSubmit != null) {
            submitMaterials = courseMaterialService.findByBoardReplyId(boardSubmit.getId());
        }


        model.addAttribute("boardSubmit", boardSubmit);
        model.addAttribute("submitMaterials", submitMaterials);

        return "/homework/submitDetail";
    }


    @GetMapping("/courses/{courseId}/homework-board/{boardId}/{boardReplyId}/new")
    public String updateHomeworkReplyForm(@PathVariable Long boardReplyId,
                                          Model model) {

        BoardReply findBoardReply = boardReplyService.findById(boardReplyId);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardReplyId(boardReplyId);


        model.addAttribute("posting", findBoardReply);
        model.addAttribute("courseMaterials", courseMaterials);

        model.addAttribute("menuTitle", "과제 제출 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/courses/{courseId}/homework-board/{boardId}/{boardReplyId}")
    public String updateHomeworkReply(@PathVariable Long courseId,
                                       @PathVariable Long boardId,
                                       @PathVariable Long boardReplyId,
                                       @RequestParam String title,
                                       @RequestParam String content,
                                       MultipartFile[] files,
                                       HttpSession session) throws IOException {

        Member loginMember = (Member) session.getAttribute("loginMember");

        boardReplyService.updateBoardReply(boardReplyId, loginMember.getId(), title, content, files);

        return "redirect:/courses/"+courseId +"/homework-board/"+boardId;
    }

    @ResponseBody
    @DeleteMapping("/courses/{courseId}/homework-board/{boardId}/{boardReplyId}")
    public ResponseEntity<String> deleteHomeworkReply(@PathVariable Long boardReplyId) {

        boardReplyService.deleteBoardReply(boardReplyId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/inquiry-board/{boardId}/new")
    public String createInquiryReplyForm(Model model){

        model.addAttribute("canUploadFile", false);
        model.addAttribute("formTitle", "문의 답변");

        return "board/boardCreateForm";
    }

    @PostMapping("/inquiry-board/{boardId}/new")
    public String createInquiryReply(@PathVariable Long boardId,
                                      String title,
                                      String content,
                                      HttpSession session) {


        Member loginMember = (Member) session.getAttribute("loginMember");

        boardReplyService.saveInquiryReply(title, content, loginMember.getId(), boardId);

        return "redirect:/inquiry-board/"+boardId;
    }

    @ResponseBody
    @DeleteMapping("/inquiry-board/{boardId}/{boardReplyId}")
    public ResponseEntity<String> deleteInquiryReply(@PathVariable Long boardReplyId) {

        boardReplyService.deleteBoardReply(boardReplyId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/inquiry-board/{boardId}/{boardReplyId}/new")
    public String updateInquiryReplyForm(@PathVariable Long boardReplyId,
                                         Model model){


        BoardReply findBoardReply = boardReplyService.findById(boardReplyId);

        model.addAttribute("posting", findBoardReply);
        model.addAttribute("menuTitle", "문의 답변 수정");
        model.addAttribute("canUploadFile", false);


        return "/board/boardUpdateForm";
    }

    @PutMapping("/inquiry-board/{boardId}/{boardReplyId}")
    public String updateInquiryReply(@PathVariable Long boardId,
                                     @PathVariable Long boardReplyId,
                                     @RequestParam String title,
                                     @RequestParam String content,
                                     HttpSession session) throws IOException{

        Member loginMember = (Member) session.getAttribute("loginMember");

        boardReplyService.updateBoardReply(boardReplyId, loginMember.getId(), title, content, null);

        return "redirect:/inquiry-board/"+ boardId;
    }

}
