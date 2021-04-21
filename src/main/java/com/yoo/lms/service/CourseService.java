package com.yoo.lms.service;


import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.StudentCourse;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.AcceptType;
import com.yoo.lms.dto.CourseListDto;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.StudentCourseRepository;
import com.yoo.lms.repository.StudentRepository;
import com.yoo.lms.searchCondition.CourseSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
@Slf4j
public class CourseService {

    private final StudentRepository studentRepository;
    private final TeacherService teacherService;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    public Course findOne(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        Course course = null;
        if(courseOptional.isPresent())
            course = courseOptional.get();

        return course;
    }

    public List<CourseListDto> findCListDtosStudent(String studentId) {
        return courseRepository
                .findCListDtosByStduent(studentId);
    }

    public List<CourseListDto> findCListDtosTeacher(String teacherId) {
        return courseRepository
                .findCListDtosByTeacher(teacherId);
    }

    public boolean existCourseName(Long courseId, String teacherId) {
        return courseRepository.existCourseName(new CourseSearchCondition(courseId, teacherId));
    }

    @Transactional
    public boolean createCourse(Course course, Teacher teacher) {

        boolean isExistCourse = courseRepository.existCourseName(new CourseSearchCondition(course.getName()));

        if(!isExistCourse) {

            course.addTeacher(teacher);
            courseRepository.save(course);
            return true;
        } else
            return false;
    }

    public List<Course> searchCourse(CourseSearchCondition condition, String studentId) {
        return courseRepository.searchCourseByStudent(condition, studentId);
    }


    @Transactional
    public synchronized Course enrollCourse(String studentId, Long courseId){

        Course findCourse = courseRepository.findById(courseId).get();

        if(findCourse.getCurrentNumStudent() == findCourse.getMaxNumStudent())
            return null;

        Student findStudent = studentRepository.findById(studentId).get();

        StudentCourse studentCourse = new StudentCourse();

        studentCourse.enrollCourse(findStudent, findCourse);

        findCourse.addCurStudentNum();
        studentCourseRepository.save(studentCourse);

        return findCourse;
    }

    public List<Course> findByTeacherIdAndAcceptType(String teacherId, AcceptType acceptType) {
        return courseRepository.findByTeacherIdAndAcceptType(teacherId, acceptType);
    }

    public List<Course> findCourseByTeacherAndType(String teacherName, AcceptType acceptType) {
        return courseRepository.findCourseByTeacherAndType(teacherName, acceptType);
    }


    @Transactional
    public void permitCourse(Long courseId) {

        Course findCourse = courseRepository.findById(courseId).get();

        findCourse.acceptCourse();
    }

    @Transactional
    public void rejectCourse(Long courseId) {

        Course findCourse = courseRepository.findById(courseId).get();

        findCourse.rejectCourse();
    }

    @Transactional
    public void updateCourse(Long courseId, Course updatedCourse) {

        Course findCourse = courseRepository.findById(courseId).get();

        findCourse.updateInfo(updatedCourse);
    }

    public boolean existCourseByTeacherIdAndCourseId(Long courseId, String teacherId){
        return courseRepository.existCourseByTeacherIdAndCourseId(courseId, teacherId);
    }

}