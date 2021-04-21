package com.yoo.lms.tools;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class PageMaker {
    
    private int currentPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private long numTotalElement;
    private int numTotalPage;
    private int pageSize;
    private int numDisplayPage;
    

    /**
     *
     * @param currentPage 현재 페이지
     * @param numTotalElement 전체 데이터 개수
     * @param pageSize 페이지 당 데이터 개수
     * @param numDisplayPage 하단 페이지 버튼 개수
     */

    public PageMaker(int currentPage, long numTotalElement, int pageSize, int numDisplayPage) {
        this.currentPage = currentPage;
        this.numTotalElement = numTotalElement;
        this.pageSize = pageSize;
        this.numDisplayPage = numDisplayPage;
        this.numTotalPage = (int) (Math.ceil(numTotalElement / (double) pageSize));
        calculatePage();
    }

    private void calculatePage() {

        endPage = (int) (Math.ceil(currentPage / (double) numDisplayPage)) * numDisplayPage;

        startPage = (endPage - numDisplayPage) + 1;

        if (endPage > numTotalPage)
            endPage = numTotalPage;

        prev = startPage != 1;
        next = (endPage * pageSize) < numTotalElement;
    }

}
