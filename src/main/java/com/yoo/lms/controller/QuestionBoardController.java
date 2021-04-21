package com.yoo.lms.controller;

import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.domain.enumType.BoardType;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.*;
import com.yoo.lms.tools.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@RequestMapping("/courses")
@Slf4j
public class QuestionBoardController {

    private final QuestionBoardService questionBoardService;
    private final CourseMaterialService courseMaterialService;
    private final BoardReplyService boardReplyService;
    private final StudentCourseService studentCourseService;
    private final CourseService courseService;

    @GetMapping("/{courseId}/question-board")
    public String listQuestion(@PathVariable Long courseId,
                               @RequestParam(defaultValue = "1") int currentPage,
                               Model model,
                               HttpSession session,
                               BoardSearchCriteria searchCriteria) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);

        if(searchCriteria.getKeyword() == null && searchCriteria.getSearchType() == null)
            searchCriteria = new BoardSearchCriteria("","");

        condition.initCondition(searchCriteria);

        Page<BoardListDto> page = null;
        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent"))
            page = questionBoardService.searchPosting(condition, true, currentPage-1, 10);

        else
            page = questionBoardService.searchPosting(condition, false, currentPage-1, 10);


        PageMaker pageMaker = new PageMaker(currentPage, page.getTotalElements());

        // 글 쓰기 가능 여부

        Member loginMember = (Member)session.getAttribute("loginMember");
        boolean canWritePosting = false;

        if(loginMember.getMemberType() == MemberType.STUDENT)
            canWritePosting = studentCourseService.existStudentCourse(courseId, loginMember.getId());

        else if (loginMember.getMemberType() == MemberType.TEACHER)
            canWritePosting = courseService.existCourseName(courseId, loginMember.getId());


        model.addAttribute("page", page);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("menuTitle", "질문 게시판");
        model.addAttribute("action", "question-board");
        model.addAttribute("canWritePosting", canWritePosting);
        
        return "/board/boardList";
    }

    @GetMapping("/{courseId}/question-board/{boardId}")
    public String showQuestion(@PathVariable Long boardId,
                               @PathVariable Long courseId,
                               Model model,
                               HttpSession session) {

        QuestionBoard findQuestion = questionBoardService.findPostingById(boardId);

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);

        questionBoardService.addViewCount(boardId);

        BoardReply boardReply = boardReplyService.findQuestionReplyByBoardId(boardId);

        findQuestion.setReply(boardReply);

        Member loginMember = (Member) session.getAttribute("loginMember");

        boolean canWriteReply = false;
        if (loginMember.getMemberType() == MemberType.TEACHER) {
            canWriteReply = courseService.existCourseByTeacherIdAndCourseId(courseId, loginMember.getId())
                    && !boardReplyService.existBoardReply(boardId, loginMember.getId(), BoardType.QUESTION);
        }

        model.addAttribute("posting", findQuestion);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("menuTitle", "질문 게시물");
        model.addAttribute("boardType", "question-board");
        model.addAttribute("canWriteReply", canWriteReply);

        return "board/boardDetailWithReply";

    }

    @GetMapping("/{courseId}/question-board/new")
    public String createQuestionForm(Model model){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("menuTitle", "질문 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/{courseId}/question-board/new")
    public String createQuestion(@PathVariable Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files,
                                 HttpSession session) throws IOException {

        Member loginMember = (Member)session.getAttribute("loginMember");

        questionBoardService.saveQuestion(files, courseId, title,content, loginMember);

        return "redirect:/courses/"+courseId+"/question-board";
    }


    @GetMapping("/{courseId}/question-board/{boardId}/update")
    public String updateQuestionForm(@PathVariable Long boardId,
                                     Model model) {

        QuestionBoard findQuestion = questionBoardService.findPostingById(boardId);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);


        model.addAttribute("posting", findQuestion);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("menuTitle", "질문 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/{courseId}/question-board/{boardId}")
    public String updateQuestion(@PathVariable Long boardId,
                                 @PathVariable Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException{

        questionBoardService.updateQuestion(boardId, title, content, files);
        
        return "redirect:/courses/"+courseId+"/question-board";

    }

    @ResponseBody
    @DeleteMapping("/{courseId}/question-board/{boardId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long boardId){

        questionBoardService.deleteByBoardId(boardId);

        return new ResponseEntity<>("ok", HttpStatus.OK);

    }

}
