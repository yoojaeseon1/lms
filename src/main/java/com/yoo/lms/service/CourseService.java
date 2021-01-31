package com.yoo.lms.service;


import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.StudentRepository;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CourseService {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final StudentCourseRepository studentCourseRepository;

    private final int pageSize = 10;

    public Course findOne(Long courseId) {
        return courseRepository.findById(courseId).get();
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    @Transactional
    public boolean createCourse(Course course) {

        List<Course> findCourse = courseRepository.findByNameContaining(course.getName());

        if(findCourse.size() == 0) {
            courseRepository.save(course);
            return true;
        }
        else
            return false;
    }

    @Transactional
    public List<Course> searchCourse(CourseSearchCondition condition) {
        return courseRepository.searchCourse(condition);
    }


    @Transactional
    public void enrollCourse(Student student, Course course){

        Student findStudent = studentRepository.findById(student.getId()).get();

        Course findCourse = courseRepository.findById(course.getId()).get();

//        System.out.println("===================");
//        System.out.println("findCourse = " + findCourse.getTeacher().getName());
//        System.out.println("===================");

        StudentCourse studentCourse = new StudentCourse();

        studentCourse.enrollCourse(findStudent, findCourse);

        studentCourseRepository.save(studentCourse);

    }

    @Transactional
    public void permitCourse(Course course) {
        Course findCourse = courseRepository.findById(course.getId()).get();

        findCourse.acceptCourse();
    }

    @Transactional
    public void updateCourse(Long courseId, Course updatedCourse) {

        Course findCourse = courseRepository.findById(courseId).get();

        findCourse.updateInfo(updatedCourse);
    }

    @Transactional
    public void cancelEnrolledCourse(Long courseId, String studentId) {
        studentCourseRepository.deleteStudentCourse(courseId, studentId);
    }

    @Transactional
    public void deleteCourse(Course course) {
        courseRepository.deleteById(course.getId());
    }
}