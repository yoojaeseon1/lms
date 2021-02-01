package com.yoo.lms.searchType;


import lombok.Getter;

@Getter
public class BoardSearchCriteria {

    private String searchType;
    private String keyword;

    public BoardSearchCriteria(String searchType, String keyword, String content) {
        this.searchType = searchType;
        this.keyword = keyword;
    }
}
