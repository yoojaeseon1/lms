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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseBoardController {

    private final CourseBoardService courseBoardService;
    private final CourseMaterialService courseMaterialService;
    private final CourseService courseService;

    @GetMapping("/course/{courseId}/courseBoard")
    public String listCourseBoard(Model model,
                               BoardSearchCriteria searchCriteria,
                               @PathVariable("courseId") Long courseId,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                                  HttpSession session) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);

//        log.info("========================");
//        log.info("courseId : "+ courseId);
//        log.info("page : " + page);
//        log.info("searchType : " + searchCriteria.getSearchType());
//        log.info("keyword : " + searchCriteria.getKeyword().equals(""));
//        log.info("keyword : " + (searchCriteria.getKeyword()==null));
//        log.info("========================");

        if(searchCriteria.getKeyword() == null && searchCriteria.getSearchType() == null)
            searchCriteria = new BoardSearchCriteria("","");

        condition.initCondition(searchCriteria);

        List<BoardListDto> postings = null;
        long totalCount = 0;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent")) {

            postings = courseBoardService.searchPostingAllCriteria(condition, page-1, 10);
            totalCount = courseBoardService.countTotalAllCriteria(condition, page-1, 10, postings.size());

        } else {

            postings = courseBoardService.searchPosting(condition, page-1, 10);
            totalCount = courseBoardService.countTotalPosting(condition, page-1, 10, postings.size());
        }


        PageMaker pageMaker = courseBoardService.makePageMaker(page, totalCount, searchCriteria);

        model.addAttribute("postings", postings);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("action","courseBoard");
        model.addAttribute("menuTitle", "강의 공지사항");



        String memberType = (String)session.getAttribute("memberType");

        boolean canWritePosting = false;
        if(memberType.equals("TEACHER")) {
            Teacher member = (Teacher) session.getAttribute("loginMember");
            String name = courseService.findName(courseId, member.getId());
            if(name != null)
                canWritePosting = true;
        }

        model.addAttribute("canWritePosting", canWritePosting);


        return "board/boardList";
    }

    @GetMapping("/course/{courseId}/courseBoard/{boardId}")
    public String showCourseBoard(Model model,
                               @PathVariable("boardId") Long boardId) {

        CourseBoard posting = courseBoardService.findPostingById(boardId);


        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);

        model.addAttribute("posting", posting);
        model.addAttribute("courseMaterials", courseMaterials);

        return "board/boardDetail";

    }

    @GetMapping("/course/{courseId}/courseBoard/new")
    public String createCourseBoardForm(Model model,
                                     @PathVariable("courseId") Long courseId,
                                     HttpServletRequest request){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("formTitle", "강의 공지사항 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/course/{courseId}/courseBoard")
    public String createCourseBoard(@PathVariable("courseId") Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files,
                                    HttpSession session
    ) throws IOException {

        Member member = (Member)session.getAttribute("loginMember");

        courseBoardService.saveCourseBoard(files, courseId, title, content, member);


        return "redirect:/course/"+courseId+"/courseBoard";
    }


    @GetMapping("/course/{courseId}/courseBoard/{boardId}/update")
    public String updateCourseBoardForm(Model model,
                                     @PathVariable("boardId") Long boardId) {

        CourseBoard findCourseBoard = courseBoardService.findPostingById(boardId);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);
//        List<String> fileNames = courseMaterialService.parseFileName(courseMaterials);


        model.addAttribute("posting", findCourseBoard);
        model.addAttribute("courseMaterials", courseMaterials);
//        model.addAttribute("fileNames", fileNames);
//        model.addAttribute("boardId", boardId);

        model.addAttribute("menuTitle", "강의 공지사항 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/course/{courseId}/courseBoard/{boardId}/")
    public String updateCourseBoard(@PathVariable("boardId") Long boardId,
                                    @PathVariable("courseId") Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException{

        courseBoardService.updateCourseBoard(boardId, title, content, files);

        return "redirect:/course/"+courseId+"/courseBoard";

    }

    @DeleteMapping("/course/{courseId}/courseBoard/{boardId}")
    public String deleteCourseBoard(@PathVariable("boardId") Long boardId,
                                    @PathVariable("courseId") Long courseId) {

        courseBoardService.deleteByBoardId(boardId);

        return "redirect:/course/"+courseId+"/courseBoard";
    }
}
