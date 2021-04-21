package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HomeworkBoardRepositoryCumstom {

    HomeworkBoard findPostingById(Long boardId);

    Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable);


}
