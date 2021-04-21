package com.yoo.lms.searchCondition;

import com.yoo.lms.searchType.BoardSearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardSearchCondition {

    private Long courseId;
    private String title;
    private String content;
    private String memberId;

    public BoardSearchCondition(Long courseId) {
        this.courseId = courseId;
    }


    public BoardSearchCondition(Long courseId, String title, String content, String memberId) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public void initCondition(BoardSearchCriteria searchCriteria) {

        switch(searchCriteria.getSearchType()) {
            case "title":
                this.title = searchCriteria.getKeyword();
                break;

            case "content":
                this.content = searchCriteria.getKeyword();
                break;

            case "titleAndContent":
                this.title = searchCriteria.getKeyword();
                this.content = searchCriteria.getKeyword();
                break;

            case "writer":
                this.memberId = searchCriteria.getKeyword();
                break;

            default :
                this.title = searchCriteria.getKeyword();
                this.memberId = searchCriteria.getKeyword();
                this.content = searchCriteria.getKeyword();
        }
    }
}
