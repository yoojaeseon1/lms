package com.yoo.lms.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QBoardRepositoryCustom {

    List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size);
    long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);
}
