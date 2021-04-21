package com.yoo.lms.controller;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.service.BoardReplyService;
import com.yoo.lms.service.InquiryBoardService;
import com.yoo.lms.tools.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/inquiry-board")
@RequiredArgsConstructor
@Slf4j
public class InquiryBoardController {

    private final InquiryBoardService inquiryBoardService;
    private final BoardReplyService boardReplyService;

    @GetMapping
    public String listBoard(@ModelAttribute BoardSearchCriteria searchCriteria,
                            @RequestParam(defaultValue = "1") int currentPage,
                            Model model,
                            HttpSession session) {


        BoardSearchCondition condition = new BoardSearchCondition();

        if(searchCriteria.getKeyword() == null && searchCriteria.getSearchType() == null)
            searchCriteria = new BoardSearchCriteria("","");

        condition.initCondition(searchCriteria);
        Page<BoardListDto> page = null;

        if(searchCriteria.getSearchType().equals("all") || searchCriteria.getSearchType().equals("titleAndContent"))
            // Pageable은 0부터 시작하므로 현재 페이지 번호에서 1을 빼줘야 순서가 맞다.
            page = inquiryBoardService.searchPosting(condition, true,currentPage - 1, 10);


         else // Pageable은 0부터 시작하므로 현재 페이지 번호에서 1을 빼줘야 순서가 맞다.
            page = inquiryBoardService.searchPosting(condition, false,currentPage - 1, 10);



        // 페이지는 1번부터 넘버링이 되야 하기 때문에 currentPage에 1을 빼지 않는다.
        PageMaker pageMaker = new PageMaker(currentPage, page.getTotalElements());

        Member loginMember = (Member)session.getAttribute("loginMember");

        boolean canWritePosting = loginMember != null;

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("page", page);
        model.addAttribute("canWritePosting", canWritePosting);
        model.addAttribute("searchCriteria", searchCriteria);
        model.addAttribute("menuTitle", "문의 게시판");
        model.addAttribute("action", "inquiry-board");

        return "/board/boardList";
    }

    @GetMapping("/{boardId}")
    public String showInquiryBoard(@PathVariable Long boardId,
                                   Model model,
                                   HttpSession session) {

        InquiryBoard posting = inquiryBoardService.findByIdFetchMember(boardId);
        BoardReply boardReply = boardReplyService.findInquiryReplyByBoardId(boardId);
        inquiryBoardService.addViewCount(boardId);

        posting.setReply(boardReply);

        Member loginMember = (Member) session.getAttribute("loginMember");

        boolean canWriteReply = false;

        if (loginMember.getMemberType() == MemberType.ADMIN
                && boardReply == null)
            canWriteReply = true;


        model.addAttribute("posting", posting);
        model.addAttribute("boardType", "inquiry-board");
        model.addAttribute("menuTitle", "문의 게시물");
        model.addAttribute("canWriteReply", canWriteReply);

        return "/board/boardDetailWithReply";
    }

    @GetMapping("/new")
    public String createInquiryForm(Model model){

        model.addAttribute("canUploadFile", false);
        model.addAttribute("menuTitle", "문의 작성");

        return "board/boardCreateForm";
    }

    @PostMapping("/new")
    public String createInquiry(@RequestParam String title,
                                @RequestParam String content,
                                HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        InquiryBoard inquiryBoard = new InquiryBoard(title, content, loginMember);

        inquiryBoardService.save(inquiryBoard);

        return "redirect:/inquiry-board";
    }

    @GetMapping("/{boardId}/update")
    public String updateInquiryForm(@PathVariable Long boardId,
                                     Model model) {

        InquiryBoard posting = inquiryBoardService.findByIdFetchMember(boardId);

        model.addAttribute("posting", posting);
        model.addAttribute("menuTitle", "질문 수정");
        model.addAttribute("canUploadFile", false);

        return "/board/boardUpdateForm";
    }

    @PutMapping("/{boardId}")
    public String updateInquiry(@PathVariable Long boardId,
                                @RequestParam String title,
                                @RequestParam String content) {

        inquiryBoardService.updateOne(boardId, title, content);

        return "redirect:/inquiry-board";

    }

    @ResponseBody
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteInquiryBoard(@PathVariable Long boardId) {

        inquiryBoardService.deleteBoardReply(boardId);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
