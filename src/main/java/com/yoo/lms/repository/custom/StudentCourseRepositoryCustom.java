package com.yoo.lms.repository.custom;

import java.util.List;

public interface StudentCourseRepositoryCustom {


    List<Long> findCourseId(String studentId);



}
