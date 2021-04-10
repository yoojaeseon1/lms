package com.yoo.lms.service;


import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.dto.CourseListDto;
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

    private final TeacherService teacherService;

    private final CourseRepository courseRepository;

    private final StudentCourseRepository studentCourseRepository;

    private final int pageSize = 10;

    public Course findOne(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        Course course = null;
        if(courseOptional.isPresent())
            course = courseOptional.get();

        return course;
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public List<CourseListDto> findCListDtosStudent(String studentId) {
        return courseRepository
                .findCListDtosByStduent(studentId);
    }

    public List<CourseListDto> findCListDtosTeacher(String teacherId) {
        return courseRepository
                .findCListDtosByTeacher(teacherId);
    }

    public String findName(Long courseId, String teacherId) {
        return courseRepository
                .findCourseName(courseId, teacherId);
    }

    @Transactional
    public boolean createCourse(Course course, String teacherId) {

        List<Course> findCourse = courseRepository.findByNameContaining(course.getName());

        if(findCourse.size() == 0) {

            Teacher teacher = teacherService.findById(teacherId);

            course.addTeacher(teacher);

            courseRepository.save(course);
            return true;
        }
        else
            return false;
    }

    @Transactional
    public List<Course> searchCourse(CourseSearchCondition condition, String studentId) {
        return courseRepository.searchCourseByStudent(condition, studentId);
    }


    @Transactional
    public boolean enrollCourse(String studentId, Long courseId){

        Course findCourse = courseRepository.findById(courseId).get();

        if(findCourse.getCurrentNumStudent() == findCourse.getMaxNumStudent())
            return false;

        Student findStudent = studentRepository.findById(studentId).get();

        StudentCourse studentCourse = new StudentCourse();

        studentCourse.enrollCourse(findStudent, findCourse);

        findCourse.addCurStudentNum();
        studentCourseRepository.save(studentCourse);

        return true;
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