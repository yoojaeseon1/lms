package com.yoo.lms.controller;

import com.yoo.lms.domain.*;
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
public class HomeworkBoardController {

    private final HomeworkBoardService homeworkBoardService;
    private final StudentCourseService studentCourseService;
    private final CourseService courseService;
    private final BoardReplyService boardReplyService;
    private final CourseMaterialService courseMaterialService;

    @GetMapping("/{courseId}/homework-board")
    public String listHomework(@PathVariable Long courseId,
                               @RequestParam(defaultValue = "1") int currentPage,
                               Model model,
                               HttpSession session,
                               BoardSearchCriteria searchCriteria) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);


        // searchCriteria.keyword가 빈칸으로 넘어가면 "" 빈문자열이 되기 때문에 동적 쿼리를 위해 null로 다시 초기화
        // 최초 페이지 이동시에는 null 이므로 null 체크 필요

        if(searchCriteria.getKeyword() == null && searchCriteria.getSearchType() == null)
            searchCriteria = new BoardSearchCriteria("","");

        // searchType, keyword가 null이면 switch문 실행 불가
        condition.initCondition(searchCriteria);

        Page<BoardListDto> page = null;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent"))
            page = homeworkBoardService.searchPosting(condition, true, currentPage - 1, 10);

        else
            page = homeworkBoardService.searchPosting(condition, false, currentPage - 1, 10);

        PageMaker pageMaker = new PageMaker(currentPage, page.getTotalElements());

        Member loginMember = (Member)session.getAttribute("loginMember");

        boolean canWritePosting = false;
        if(loginMember.getMemberType() == MemberType.TEACHER) {
            canWritePosting = courseService.existCourseName(courseId, loginMember.getId());
        }

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("page", page);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("menuTitle", "과제 게시판");
        model.addAttribute("action", "homework-board");
        model.addAttribute("canWritePosting", canWritePosting);

        return "/board/boardList";
    }

    @GetMapping("/{courseId}/homework-board/{boardId}")
    public String showHomework(@PathVariable Long boardId,
                               @PathVariable Long courseId,
                               Model model,
                               HttpSession session) {


        Member loginMember = (Member) session.getAttribute("loginMember");
        HomeworkBoard homeworkBoard = homeworkBoardService.findPostingById(boardId, false);
        BoardReply boardReply = boardReplyService.findByBoardIdAndStudentId(boardId, loginMember.getId());

        List<CourseMaterial> submitMaterials = null;

        if(boardReply != null)
            submitMaterials = courseMaterialService.findByBoardReplyId(boardReply.getId());

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);
        homeworkBoardService.addViewCount(boardId);

        boolean canWriteReply = false;
        boolean canCheckHomeworks = false;

        if (loginMember.getMemberType() == MemberType.STUDENT) {
            canWriteReply = studentCourseService.existStudentCourse(courseId, loginMember.getId())
                    && !boardReplyService.existBoardReply(boardId, loginMember.getId(), BoardType.HOMEWORK);
        } else if(loginMember.getMemberType() == MemberType.TEACHER) {
             canCheckHomeworks = courseService.existCourseName(courseId, loginMember.getId());
        }

        model.addAttribute("posting", homeworkBoard);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("canWriteReply", canWriteReply);
        model.addAttribute("menuTitle", "과제 게시물");
        model.addAttribute("boardType", "homework-board");
        model.addAttribute("boardReply", boardReply);
        model.addAttribute("submitMaterials", submitMaterials); // 과제 제출 파일
        model.addAttribute("canCheckHomewroks", canCheckHomeworks);

        return "/board/boardDetailWithReply";

    }

    @GetMapping("/{courseId}/homework-board/new")
    public String createHomeworkForm(Model model){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("menuTitle", "과제 공지 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/{courseId}/homework-board/new")
    public String createHomework(@PathVariable Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files,
                                 HttpSession session
                                 ) throws IOException {

        Member loginMember = (Member) session.getAttribute("loginMember");

        homeworkBoardService.saveHomework(files, courseId, title,content,loginMember);

        return "redirect:/courses/"+courseId+"/homework-board";
    }


    @GetMapping("/{courseId}/homework-board/{boardId}/update")
    public String updateHomeworkForm(@PathVariable Long boardId,
                                     Model model) {

        HomeworkBoard homeworkBoard = homeworkBoardService.findPostingById(boardId, false);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);


        model.addAttribute("posting", homeworkBoard);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("menuTitle", "과제 공지 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/{courseId}/homework-board/{boardId}")
    public String updateHomework(@PathVariable Long boardId,
                                 @PathVariable Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException{

        homeworkBoardService.updateHomework(boardId, title, content, files);

        return "redirect:/courses/"+courseId+"/homework-board";

    }

    @ResponseBody
    @DeleteMapping("/{courseId}/homework-board/{boardId}")
    public ResponseEntity<String> deleteHomework(@PathVariable Long boardId,
                                                @PathVariable Long courseId) {

        homeworkBoardService.deleteByBoardId(boardId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
