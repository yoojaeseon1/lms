package com.yoo.lms.service;

import com.yoo.lms.domain.InquiryBoard;
import com.yoo.lms.domain.View;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.InquiryBoardRepository;
import com.yoo.lms.repository.ViewRepository;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InquiryBoardService {

    private final InquiryBoardRepository inquiryBoardRepository;
    private final ViewRepository viewRepository;

    @Transactional
    public void save(InquiryBoard inquiryBoard){
        inquiryBoardRepository.save(inquiryBoard);
    }

    public InquiryBoard findByIdFetchMember(Long id) {
        return inquiryBoardRepository.findByIdFetchMember(id);
    }

    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, int currentPage, int limit) {

        Page<BoardListDto> page = inquiryBoardRepository.searchPosting(condition, isMultipleCriteria, PageRequest.of(currentPage, limit));

        return page;

    }

    @Transactional
    public void addViewCount(Long boardId){

        InquiryBoard inquiryBoard = inquiryBoardRepository.findById(boardId).get();
        View view = new View(inquiryBoard);

        viewRepository.save(view);
        view.addViewCount(inquiryBoard);

    }

    @Transactional
    public void updateOne(Long boardId, String title, String content) {
        InquiryBoard findPosting = inquiryBoardRepository.findById(boardId).get();

        findPosting.updateInfo(title, content);
    }


    @Transactional
    public void deleteBoardReply(Long boardId) {

        viewRepository.deleteAllByBoardId(boardId);
        inquiryBoardRepository.deleteById(boardId);

    }


}
