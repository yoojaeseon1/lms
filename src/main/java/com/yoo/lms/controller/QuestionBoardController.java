package com.yoo.lms.controller;

import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.CourseMaterialService;
import com.yoo.lms.service.QuestionBoardService;
import com.yoo.lms.tools.PageMaker;
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
@Slf4j
public class QuestionBoardController {

    @Autowired
    QuestionBoardService questionBoardService;

    @Autowired
    CourseMaterialService courseMaterialService;

    @GetMapping("/{courseId}/question")
    public String listQuestion(Model model,
                       BoardSearchCriteria searchCriteria,
                       @PathVariable("courseId") Long courseId,
                       @RequestParam("page") int page) {

        BoardSearchCondition condition = new BoardSearchCondition(courseId);
        condition.initCondition(searchCriteria);

        List<BoardListDto> postings = null;
        long totalCount = 0;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent")) {

            postings = questionBoardService.searchPostingAllCriteria(condition, page-1, 10);
            totalCount = questionBoardService.countTotalAllCriteria(condition, page-1, 10, postings.size());

        } else {

            postings = questionBoardService.searchPosting(condition, page-1, 10);
            totalCount = questionBoardService.countTotalPosting(condition, page-1, 10, postings.size());
        }


        PageMaker pageMaker = questionBoardService.makePageMaker(page, totalCount, searchCriteria);
        
        model.addAttribute("postings", postings);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        model.addAttribute("menuTitle", "질문 게시판");
        model.addAttribute("action", "question");
        
        return "board/boardList";
    }

    @GetMapping("/{courseId}/question/{boardId}")
    public String showQuestion(Model model,
                               @PathVariable("boardId") Long boardId) {

        QuestionBoard findQuestion = questionBoardService.findPostingById(boardId);

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);

        model.addAttribute("posting", findQuestion);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("menuTitle", "질문 게시물");

        return "boardDetailWithReply";

    }

    @GetMapping("/{courseId}/question/new")
    public String createQuestionForm(Model model,
                                     @PathVariable("courseId") Long courseId,
                                     HttpServletRequest request){

        model.addAttribute("canUploadFile", true);
        model.addAttribute("menuTitle", "질문 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/{courseId}/question")
    public String createQuestion(@PathVariable("courseId") Long courseId,
                                 String title,
                                 String content,
                                 MultipartFile[] files
                                 ) throws IOException {

        questionBoardService.saveQuestion(files, courseId, title,content);


        return "redirect:/";
    }


    @GetMapping("/{courseId}/question/{boardId}/update")
    public String updateQuestionForm(Model model,
                                     @PathVariable("boardId") Long boardId) {

        QuestionBoard findQuestion = questionBoardService.findPostingById(boardId);
        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);
//        List<String> fileNames = courseMaterialService.parseFileName(courseMaterials);


        model.addAttribute("posting", findQuestion);
        model.addAttribute("courseMaterials", courseMaterials);
//        model.addAttribute("fileNames", fileNames);
//        model.addAttribute("boardId", boardId);

        model.addAttribute("menuTitle", "질문 수정");
        model.addAttribute("canUploadFile", true);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/{courseId}/question/{boardId}/")
    public String updateQuestion(@PathVariable("boardId") Long boardId,
                                 String title,
                                 String content,
                                 MultipartFile[] files) throws IOException{

        questionBoardService.updateQuestion(boardId, title, content, files);
        
        return "redirect:/";

    }

    @DeleteMapping("{courseId}/question/{boardId}")
    public String deleteQuestion(@PathVariable("boardId") Long boardId) {

        questionBoardService.deleteByBoardId(boardId);

        return "redirect:/";

    }

}
