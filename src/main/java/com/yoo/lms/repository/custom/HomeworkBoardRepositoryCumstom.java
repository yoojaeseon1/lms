package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeworkBoardRepositoryCumstom {

    HomeworkBoard findPostingById(Long boardId, boolean hasReplies);

    Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, Pageable pageable);


}
