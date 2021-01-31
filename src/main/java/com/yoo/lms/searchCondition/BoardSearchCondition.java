package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.searchType.BoardSearchCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class BoardSearchCondition {

    private Long courseId;
    private String title;
    private String content;
    private String memberId;
    private MemberType memberType;

    public BoardSearchCondition(Long courseId) {
        this.courseId = courseId;
    }

    public BoardSearchCondition(Long courseId, String title, String content, String memberId, MemberType memberType) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.memberType = memberType;
    }

    public void initCondition(BoardSearchCriteria searchCriteria) {

        switch(searchCriteria.getSearchType()) {
            case "title":
                this.title = searchCriteria.getKeyword();
                break;

            case "writer":
                this.memberId = searchCriteria.getKeyword();
                break;

            case "content":
                this.content = searchCriteria.getContent();
        }

    }



}
