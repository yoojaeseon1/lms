package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.CourseMaterial;

import java.util.List;

public interface CourseMaterialRepositoryCustom {

    List<CourseMaterial> findByBoardId(Long boardId);

    List<CourseMaterial> findByBoardReplyId(Long boardReplyId);

    long deleteAllByBoardId(Long boardId);

    long deleteAllByBoardReplyId(Long boardReplyId);


}
