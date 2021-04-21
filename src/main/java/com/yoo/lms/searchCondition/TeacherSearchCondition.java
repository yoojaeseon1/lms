package com.yoo.lms.searchCondition;

import com.yoo.lms.domain.enumType.AcceptType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TeacherSearchCondition {

    private String name;
    private String id;
    private AcceptType acceptType;


    public TeacherSearchCondition(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public TeacherSearchCondition(String name, String id, AcceptType acceptType) {
        this.name = name;
        this.id = id;
        this.acceptType = acceptType;
    }
}
