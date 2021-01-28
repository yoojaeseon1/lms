package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.MemberType;
import lombok.Getter;

@Getter
public class BoardSearchCondition {

    public BoardSearchCondition(String title, String content, String memberId, MemberType memberType) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.memberType = memberType;
    }

    private String title;
    private String content;
    private String memberId;
    private MemberType memberType;


}
