package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudentRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentCourseRepository studentCourseRepository;


    @Test
    public void enroll(){

        //given
        Course course = courseRepository.findById(1L).get();

//        System.out.println("course.getTeacher().getName() = " + course.getTeacher().getName());

        Student student = studentRepository.findById("yoo1").get();

        courseService.enrollCourse(student, course);

        em.flush();
        em.clear();

        //when
        StudentCourse studentCourse = studentCourseRepository.findById(2L).get();


        // then
        assertThat(studentCourse.getStudent().getName()).isEqualTo("yoo1");

    }

}