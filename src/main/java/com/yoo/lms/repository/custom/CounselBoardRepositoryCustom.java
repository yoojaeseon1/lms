package com.yoo.lms.repository.custom;

import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;

import java.util.List;

public interface CounselBoardRepositoryCustom {

    List<BoardListDto> searchByDynamic(BoardSearchCondition condition, int page, int size);

    long countTotalByDynamic(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);


}
