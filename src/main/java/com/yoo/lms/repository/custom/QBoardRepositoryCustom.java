package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QBoardRepositoryCustom {

    QuestionBoard findPostingById(Long boardId);

    Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable);



}
