package com.yoo.lms.repository.impl;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.repository.TeacherRepository;
import com.yoo.lms.searchCondition.TeacherSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    public void searchTeachersBySearchCondition(){

        //given
        AcceptType acceptType = AcceptType.WAITING;
        TeacherSearchCondition searchCondition = new TeacherSearchCondition(null, "teacherid1", acceptType);

        //when

        List<Teacher> teachers = teacherRepository.searchTeachersBySearchCondition(searchCondition);


        //then

        assertThat(teachers.size()).isEqualTo(0);

    }

}