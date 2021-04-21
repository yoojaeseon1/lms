package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.domain.enumType.BoardType;

import java.util.List;

public interface BoardReplyRepositoryCustom {

    BoardReply findByBoardIdAndStudentId(Long boardId, String studentId);

    BoardReply findQuestionReplyByBoardId(Long boardId);

    BoardReply findInquiryReplyByBoardId(Long boardId);

    List<BoardReply> findHomeworkRepliesByBoardId(Long boardId);

    boolean existBoardReply(Long boardId, String memberId, BoardType boardType);

}
