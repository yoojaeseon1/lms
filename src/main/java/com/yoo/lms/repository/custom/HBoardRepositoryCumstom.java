package com.yoo.lms.repository.custom;

import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;

import java.util.List;

public interface HBoardRepositoryCumstom {

    List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size);
    long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);

}
