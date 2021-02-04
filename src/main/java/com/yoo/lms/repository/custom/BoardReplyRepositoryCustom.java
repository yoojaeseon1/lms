package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.BoardReply;

public interface BoardReplyRepositoryCustom {

    BoardReply findByIdAndMember(Long boardId, String studentId);

}
