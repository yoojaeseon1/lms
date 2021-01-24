package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourseRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Autowired
    MemberRepository memberRepository;



//    @BeforeEach
//    public void before(){
//
//        Student student = new Student("yoo1", "1234", "name1", 12, new Address("1","2","3"));
//        Teacher teacher = new Teacher("yoo2", "1234", "name2", 12, new Address("1","2","3"));
//        memberRepository.save(student);
//        memberRepository.save(teacher);
//
//
//        System.out.println("before course insert================");
//        Course course = new Course("course1", teacher);
//        System.out.println("after course insert================");
//        em.flush();
//
//        courseRepository.save(course);
//
//    }

    @Test
    public void enrollCourse(){

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

    @Test
    public void enrollCourseRepository(){

        //given
        Course course = courseRepository.findById(1L).get();

//        System.out.println("course.getTeacher().getName() = " + course.getTeacher().getName());

        Student student = studentRepository.findById("yoo1").get();

        StudentCourse studentCourse = new StudentCourse();

        studentCourse.enrollCourse(student, course);

        studentCourseRepository.save(studentCourse);

//        System.out.println("===================");
//        System.out.println(studentCourse.getCourse().getTeacher().getName());
//        System.out.println("===================");

        em.flush();
        em.clear();

        //when
        StudentCourse findStudentCourse = studentCourseRepository.findById(2L).get();


        // then
        assertThat(findStudentCourse.getStudent().getName()).isEqualTo("name1");

    }

    @Test
    public void updateInfo(){

        //given
        Course findCourse = courseRepository.findById(1L).get();
        Course updateCourse = new Course("updatedName", findCourse.getTeacher());

        //when
        findCourse.updateInfo(updateCourse);
        em.flush();

        Course updatedCourse = courseRepository.findById(1L).get();

        //then

        assertThat(updatedCourse.getName()).isEqualTo("updatedName");

    }

    @Test
    public void findByPermission(){

        //given


        //when
        List<Course> courses = courseRepository.findByPermission(false);


        //then

        assertThat(courses.get(0).getName()).isEqualTo("course1");

    }

    @Test
    public void findByTeacherName(){

        //given


        //when
        List<Course> courses = courseRepository.findByTeacherName("name2");

        //then

        assertThat(courses.get(0).getName()).isEqualTo("course1");

    }

    @Test
    public void deleteCourse(){

        //given


        //when

        courseRepository.deleteById(1L);

        List<Course> courses = courseRepository.findAll();

        //then

        assertThat(courses.get(0).getName()).isEqualTo("course2");

    }



}