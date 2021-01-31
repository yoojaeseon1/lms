package com.yoo.lms.service;


import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.QuestionBoardRepository;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.tools.PageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionBoardService {

    @Autowired
    QuestionBoardRepository questionBoardRepository;

    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {
        return questionBoardRepository.searchPosting(condition, page, size);
    }

    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        return questionBoardRepository.countTotalPosting(condition, page, size, numCurrentPageContent);
    }

    public PageMaker makePageMaker(int page, long totalCount, BoardSearchCriteria searchCriteria) {

        return new PageMaker(page, totalCount, searchCriteria);

    }



}
