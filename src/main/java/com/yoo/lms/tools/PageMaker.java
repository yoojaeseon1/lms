package com.yoo.lms.tools;

import com.yoo.lms.searchType.BoardSearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@NoArgsConstructor
@Component
public class PageMaker {

    private int currentPage;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private long numTotalContent;
    private int numTotalPage;

    private int pageSize; // 한 페이지 당 데이터 개수
    private int numDisplayPage; // 페이지 번호의 개수(밑에 10개의 페이지를 이동가능한 버튼의 개수)

    public PageMaker(int currentPage, long totalCount) {
        this.currentPage = currentPage;
        this.numTotalContent = totalCount;
        this.pageSize = 10;
        this.numDisplayPage = 10;
        this.numTotalPage = (int) (Math.ceil(totalCount / (double) pageSize));
        calcData();
    }

    private void calcData() {

        endPage = (int) (Math.ceil(currentPage / (double) numDisplayPage)) * numDisplayPage;

        startPage = (endPage - numDisplayPage) + 1;

        if (endPage > numTotalPage)
            endPage = numTotalPage;

        prev = startPage == 1 ? false : true;

        next = endPage * pageSize >= numTotalContent ? false : true;
    }

}
