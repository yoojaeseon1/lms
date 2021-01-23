package com.yoo.lms.domain.service;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.MemberRepository;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StudentServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @BeforeEach
    public void before(){

        Student student = new Student("yoo1", "1234", "name1", 12, new Address("1","2","3"));
        Teacher teacher = new Teacher("yoo2", "1234", "name2", 12, new Address("1","2","3"));
        memberRepository.save(student);
        memberRepository.save(teacher);

        Course course = new Course("course1", teacher);

        courseRepository.save(course);

    }

    @Test
    public void enroll(){

        //given
        Course course = courseRepository.findById(1L).get();

//        System.out.println("course.getTeacher().getName() = " + course.getTeacher().getName());

        Student student = studentRepository.findById("yoo1").get();

        studentService.enrollCourse(student, course);

        em.flush();
        em.clear();

        //when
        StudentCourse studentCourse = studentCourseRepository.findById(2L).get();


        // then
        assertThat(studentCourse.getStudent().getName()).isEqualTo("yoo1");

    }

}