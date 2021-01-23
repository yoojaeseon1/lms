package com.yoo.lms.domain.service;


import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentCourseRepository studentCourseRepository;

    @Transactional
    public void enrollCourse(Student student, Course course){

        Student findStudent = studentRepository.findById(student.getId()).get();

        Course findCourse = courseRepository.findById(course.getId()).get();

        System.out.println("===================");
        System.out.println("findCourse = " + findCourse.getTeacher().getName());
        System.out.println("===================");

        StudentCourse studentCourse = new StudentCourse();

        studentCourse.enrollCourse(findStudent, findCourse);

        studentCourseRepository.save(studentCourse);

    }

}
