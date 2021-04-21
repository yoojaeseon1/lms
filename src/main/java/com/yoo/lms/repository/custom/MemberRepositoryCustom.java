package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.searchCondition.MemberSearchCondition;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Member searchMember(MemberSearchCondition searchCondition);

    boolean isExistId(String id);

    MemberType searchMemberType(MemberSearchCondition searchCondition);

}
