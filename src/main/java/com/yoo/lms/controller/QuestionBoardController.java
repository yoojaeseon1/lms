package com.yoo.lms.controller;

import com.yoo.lms.domain.Board;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.QuestionBoardService;
import com.yoo.lms.tools.PageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionBoardController {

    @Autowired
    QuestionBoardService questionBoardService;

    @GetMapping("{courseId}/question")
    public String main(Model model,
                       BoardSearchCriteria searchCriteria,
                       @PathVariable("courseId") Long courseId,
                       @RequestParam("page") int page) {

        System.out.println("courseId = " + courseId);
        System.out.println("page = " + page);
        System.out.println("searchCriteria = " + searchCriteria.getSearchType());
        System.out.println("keyword = " + searchCriteria.getKeyword());

        BoardSearchCondition condition = new BoardSearchCondition(courseId);
        condition.initCondition(searchCriteria);

        List<BoardListDto> postings = questionBoardService.searchPosting(condition, page, 10);

        long totalCount = questionBoardService.countTotalPosting(condition, page, 10, postings.size());

        PageMaker pageMaker = questionBoardService.makePageMaker(page, totalCount, searchCriteria);

        System.out.println("pageMaker.makeSearch(page) = " + pageMaker.makeSearch(page));
        
        model.addAttribute("postings", postings);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("courseId", courseId);
        
        return "board/questionBoard";
    }

}
