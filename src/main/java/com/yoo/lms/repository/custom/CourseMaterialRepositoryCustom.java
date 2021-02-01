package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.CourseMaterial;

import java.util.List;

public interface CourseMaterialRepositoryCustom {

    List<CourseMaterial> findByBoardId(Long boardId);

}
