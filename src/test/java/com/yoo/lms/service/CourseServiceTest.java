package com.yoo.lms.service;

import com.yoo.lms.domain.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    EntityManager em;

    @Test
    public void updateInfo(){

        //given
        Course course = courseService.findOne(1L);

        Course updateCourse = new Course("newCourse",  50, LocalDate.now(), LocalDate.now());
        courseService.updateCourse(course.getId(), updateCourse);
        em.flush();

        //when

        Course updatedCourse = courseService.findOne(1L);

        //then

        assertThat(updatedCourse.getName()).isEqualTo("newCourse");

    }

}