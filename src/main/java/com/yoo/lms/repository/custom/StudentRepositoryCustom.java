package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Student;
import com.yoo.lms.searchCondition.MemberSearchCondition;

public interface StudentRepositoryCustom {

    Student searchStudent(MemberSearchCondition searchCondition);



}
