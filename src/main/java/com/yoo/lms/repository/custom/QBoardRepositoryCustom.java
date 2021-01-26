package com.yoo.lms.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yoo.lms.dto.BoardListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QBoardRepositoryCustom {

    List<BoardListDto> searchByAllCriteria(String keyword, Pageable pageable);
    long countTotalByAllCriteria(String keyword);

    List<BoardListDto> searchByTitle(String title, Pageable pageable);
    long countTotalByTitle(String title);

    List<BoardListDto> searchByContent(String content, Pageable pageable);
    long countTotalByContent(String content);

    List<BoardListDto> searchByWriter(String writer, Pageable pageable);
    long countTotalByWriter(String writer);

    List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable);
    long createTotalCountQuery(BooleanExpression condition);
}
