package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.HomeworkBoard;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;

import java.util.List;

public interface HBoardRepositoryCumstom {

    HomeworkBoard findPostingById(Long boardId, boolean hasReplies);

    long deletePostingById(Long boardId);

    List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size);
    long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);

    List<BoardListDto> searchPostingAllCriteria(BoardSearchCondition condition, int page, int size);
    long countTotalAllCriteria(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);

}
