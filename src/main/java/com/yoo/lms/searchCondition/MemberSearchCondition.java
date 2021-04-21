package com.yoo.lms.searchCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class MemberSearchCondition {

    private String id;
    private String password;
    private String name;
    private String email;

    public MemberSearchCondition(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
