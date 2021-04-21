package com.yoo.lms.repository.custom;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.searchCondition.TeacherSearchCondition;

import java.util.List;

public interface TeacherRepositoryCustom {

    List<Teacher> searchTeachersBySearchCondition(TeacherSearchCondition searchCondition);

}
