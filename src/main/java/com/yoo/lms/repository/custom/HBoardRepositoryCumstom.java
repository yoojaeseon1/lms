package com.yoo.lms.repository.custom;

import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;

import java.util.List;

public interface HBoardRepositoryCumstom {

    List<BoardListDto> searchByDynamic(BoardSearchCondition condition, int page, int size);
    long countTotalByDinamic(BoardSearchCondition condition, int page, int size, int numCurrentPageContent);

//    List<BoardListDto> searchAll(int page, int size);
//    long countTotalAll(int page, int size, int numCurrentPageContent);
//
//    List<BoardListDto> searchByAllCriteria(String keyword, int page, int size);
//    long countTotalByAllCriteria(String keyword, int page, int size, int numCurrentPageContent);
//
//    List<BoardListDto> searchByTitle(String title, int page, int size);
//    long countTotalByTitle(String title, int page, int size, int numCurrentPageContent);
//
//    List<BoardListDto> searchByContent(String content, int page, int size);
//    long countTotalByContent(String content, int page, int size, int numCurrentPageContent);
//
//    List<BoardListDto> searchByWriter(String writer, int page, int size);
//    long countTotalByWriter(String writer, int page, int size, int numCurrentPageContent);
//
//    List<BoardListDto> createSearchQuery(BooleanExpression condition, Pageable pageable);
//    long createTotalCountQuery(BooleanExpression condition);



}
