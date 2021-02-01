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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

        System.out.println("courseId = " + courseId);
        System.out.println("page = " + page);
        System.out.println("searchCriteria = " + searchCriteria.getSearchType());
        System.out.println("keyword = " + searchCriteria.getKeyword());

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

//        System.out.println("pageMaker.makeSearch(page) = " + pageMaker.makeSearch(page));
        
        model.addAttribute("postings", postings);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        
        return "board/question/questionList";
    }

    @GetMapping("/{courseId}/question/{boardId}")
    public String showQuestion(Model model,
                               @PathVariable("boardId") Long boardId) {

        QuestionBoard findQuestion = questionBoardService.findById(boardId);

        List<CourseMaterial> courseMaterials = courseMaterialService.findByBoardId(boardId);

        List<String> fileNames = courseMaterialService.parseFileName(courseMaterials);

        model.addAttribute("findQuestion", findQuestion);
        model.addAttribute("courseMaterials", courseMaterials);
        model.addAttribute("fileNames", fileNames);

        return "board/question/questionDetail";

    }

    @GetMapping("/{courseId}/question/new")
    public String createQuestionForm(Model model,
                                     @PathVariable("courseId") Long courseId){

        model.addAttribute("courseId", courseId);

        return "board/question/questionCreateForm";
    }

    @PostMapping("/{boardId}/question/new")
    public String createQuestion(MultipartFile[] files,
                                 @PathVariable("boardId") Long boardId,
                                 String title,
                                 String content
                                 ) throws IOException {

        log.info("================");
        log.info("title : " + title);
        log.info("content : " + content);
        log.info("================");

        questionBoardService.save(boardId, title,content, files);

//        questionBoardService.save(files);

        return "redirect:/";
    }

}
