package com.yoo.lms.service;

import com.yoo.lms.dto.MyCourseDto;
import com.yoo.lms.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;


    public List<MyCourseDto> findMyCourseDto(String studentId) {
        return studentCourseRepository.findMyCourse(studentId);
    }

    public boolean existStudentCourse(Long courseId, String studentId) {
        return studentCourseRepository.existStudentCourse(courseId, studentId);
    }

}
