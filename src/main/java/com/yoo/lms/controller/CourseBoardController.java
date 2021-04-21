package com.yoo.lms.controller;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.CourseBoardService;
import com.yoo.lms.service.CourseMaterialService;
import com.yoo.lms.service.CourseService;
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
public class CourseBoardController {

    private final CourseBoardService courseBoardService;
    private final CourseMaterialService courseMaterialService;
    private final CourseService courseService;

    @GetMapping("/{courseId}/course-board/new")
    public String createCourseBoardForm(Model model){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("formTitle", "강의 공지사항 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/{courseId}/course-board/new")
    public String createCourseBoard(@PathVariable Long courseId,
                                    String title,
                                    String content,
                                    MultipartFile[] files,
                                    HttpSession session) throws IOException {

        Member member = (Member)session.getAttribute("loginMember");

        courseBoardService.saveCourseBoard(files, courseId, title, content, member);


        return "redirect:/courses/"+courseId+"/course-board";
    }

    @GetMapping("/{courseId}/course-board")
    public String listCourseBoard(Model model,
                                  BoardSearchCriteria searchCriteria,
                                  @PathVariable("courseId") Long courseId,
                                  @RequestParam(defaultValue = "1") int currentPage,
                                  HttpSession session) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);


        if(searchCriteria.getKeyword() == null && searchCriteria.getSearchType() == null)
            searchCriteria = new BoardSearchCriteria("","");

        condition.initCondition(searchCriteria);

        Page<BoardListDto> page = null;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent"))
            page = courseBoardService.searchPosting(condition, true, currentPage-1, 10);

        else
            page = courseBoardService.searchPosting(condition, false, currentPage-1, 10);


        PageMaker pageMaker = new PageMaker(currentPage, page.getTotalElements(),10, 10);

        model.addAttribute("page", page);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("action","course-board");
        model.addAttribute("menuTitle", "강의 공지사항");



        MemberType memberType = (MemberType)session.getAttribute("memberType");

        boolean canWritePosting = false;

        if(memberType == MemberType.TEACHER) {
            Teacher member = (Teacher) session.getAttribute("loginMember");
            boolean isExistCourseName = courseService.existCourseName(courseId, member.getId());

            if(isExistCourseName)
                canWritePosting = true;
        }

        model.addAttribute("canWritePosting", canWritePosting);


        return "/board/boardList";
    }

    @GetMapping("/{courseId}/course-board/{boardId}")
    public String showCourseBoard(@PathVariable Long boardId,
                                  Model model) {

        CourseBoard posting = courseBoardService.findPostingById(boardId);
        courseBoardService.addViewCount(boardId);

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);


        model.addAttribute("posting", posting);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("boardType", "course-board");

        return "board/boardDetail";

    }


    @GetMapping("/{courseId}/course-board/{boardId}/update")
    public String updateCourseBoardForm(@PathVariable Long boardId,
                                        Model model) {

        CourseBoard findCourseBoard = courseBoardService.findPostingById(boardId);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);


        model.addAttribute("posting", findCourseBoard);
        model.addAttribute("courseMaterials", courseMaterials);

        model.addAttribute("menuTitle", "강의 공지사항 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/{courseId}/course-board/{boardId}")
    public String updateCourseBoard(@PathVariable Long boardId,
                                    @PathVariable Long courseId,
                                    @RequestParam String title,
                                    @RequestParam String content,
                                    MultipartFile[] files) throws IOException{

        courseBoardService.updateCourseBoard(boardId, title, content, files);

        return "redirect:/courses/"+courseId+"/course-board";

    }

    @ResponseBody
    @DeleteMapping("/{courseId}/course-board/{boardId}")
    public ResponseEntity<String> deleteCourseBoard(@PathVariable Long boardId) {

        courseBoardService.deleteByBoardId(boardId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}

