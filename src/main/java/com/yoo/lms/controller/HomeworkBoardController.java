package com.yoo.lms.controller;

import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.CourseMaterialService;
import com.yoo.lms.service.HomeworkBoardService;
import com.yoo.lms.tools.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeworkBoardController {

    private final HomeworkBoardService homeworkBoardService;

    @Autowired
    CourseMaterialService courseMaterialService;

    @GetMapping("/course/{courseId}/homework")
    public String listHomework(Model model,
                               BoardSearchCriteria searchCriteria,
                               @PathVariable("courseId") Long courseId,
                               @RequestParam("page") int page) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);
        condition.initCondition(searchCriteria);

        List<BoardListDto> postings = null;
        long totalCount = 0;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent")) {

            postings = homeworkBoardService.searchPostingAllCriteria(condition, page-1, 10);
            totalCount = homeworkBoardService.countTotalAllCriteria(condition, page-1, 10, postings.size());

        } else {

            postings = homeworkBoardService.searchPosting(condition, page-1, 10);
            totalCount = homeworkBoardService.countTotalPosting(condition, page-1, 10, postings.size());
        }


        PageMaker pageMaker = homeworkBoardService.makePageMaker(page, totalCount, searchCriteria);

        model.addAttribute("postings", postings);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("menuTitle", "과제 게시판");
        model.addAttribute("action", "homework");

        return "board/boardList";
    }

    @GetMapping("/course/{courseId}/homework/{boardId}")
    public String showHomework(Model model,
                               @PathVariable("boardId") Long boardId) {

        HomeworkBoard homeworkBoard = homeworkBoardService.findPostingById(boardId, false);

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);

        model.addAttribute("posting", homeworkBoard);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("menuTitle", "과제 게시물");

        return "board/detail/homework";

    }

    @GetMapping("/course/{courseId}/homework/new")
    public String createHomeworkForm(Model model,
                                     @PathVariable("courseId") Long courseId,
                                     HttpServletRequest request){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("menuTitle", "과제 공지 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/course/{courseId}/homework")
    public String createHomework(@PathVariable("courseId") Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException {

        homeworkBoardService.saveHomework(files, courseId, title,content);


        return "redirect:/";
    }


    @GetMapping("/course/{courseId}/homework/{boardId}/update")
    public String updateHomeworkForm(Model model,
                                     @PathVariable("boardId") Long boardId) {

        HomeworkBoard homeworkBoard = homeworkBoardService.findPostingById(boardId, false);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);
//        List<String> fileNames = courseMaterialService.parseFileName(courseMaterials);


        model.addAttribute("posting", homeworkBoard);
        model.addAttribute("courseMaterials", courseMaterials);
//        model.addAttribute("fileNames", fileNames);
//        model.addAttribute("boardId", boardId);

        model.addAttribute("menuTitle", "과제 공지 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/course/{courseId}/homework/{boardId}/")
    public String updateHomework(@PathVariable("boardId") Long boardId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException{

        homeworkBoardService.updateHomework(boardId, title, content, files);

        return "redirect:/";

    }

    @DeleteMapping("/course/{courseId}/homework/{boardId}")
    public String deleteHomework(@PathVariable("boardId") Long boardId) {

        homeworkBoardService.deleteByBoardId(boardId);

        return "redirect:/";

    }

}
