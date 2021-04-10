package com.yoo.lms.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudentCourseRepositoryTest {
    
    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Test
    public void deleteStudentCourse(){

        //given
        Long courseId = 1L;
        String studentId = "studentId1";

        //when

        int removeCount = studentCourseRepository.deleteStudentCourse(courseId, studentId);


        //then

        assertThat(removeCount).isEqualTo(1);

    }

    @Test
    public void findCourseId(){

        //given

        String studentId = "studentid1";

        //when

        List<Long> courseIds = studentCourseRepository.findCourseId(studentId);

//        for (Long courseId : courseIds) {
//            System.out.println("courseId = " + courseId);
//        }

        //then

        assertThat(courseIds.size()).isEqualTo(2);

    }

}