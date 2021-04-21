package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.searchCondition.MemberSearchCondition;

public interface MemberRepositoryCustom {

    Member searchMember(MemberSearchCondition searchCondition);

    boolean existId(String id);

    MemberType searchMemberType(MemberSearchCondition searchCondition);

}
