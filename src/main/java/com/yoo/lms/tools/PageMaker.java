package com.yoo.lms.tools;

import org.springframework.web.util.UriComponents;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {

    private int currentPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int totalCount;

    private int pageSize; // 한 페이지 당 데이터 개수
    private int displayPageNum; // 페이지 번호의 개수(밑에 10개의 페이지를 이동가능한 버튼의 개수)

    public PageMaker(int currentPage, int totalCount) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = 10;
        this.displayPageNum = 10;
    }

    private void calcData() {

        endPage = (int) (Math.ceil(currentPage / (double) displayPageNum)) * displayPageNum;

        startPage = (endPage - displayPageNum) + 1;

        int tempEndPage = (int) (Math.ceil(totalCount / (double) pageSize));

        if (endPage > tempEndPage)
            endPage = tempEndPage;

        prev = startPage == 1 ? false : true;

        next = endPage * pageSize >= totalCount ? false : true;
    }


    public String makeSearch(int page) {

        UriComponents uriComponents = null;

        return uriComponents.toString();

    }


    private String encoding(String keyword) {
        if (keyword == null || keyword.trim().length() == 0)
            return "";

        try {
            return URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
