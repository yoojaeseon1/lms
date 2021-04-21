package com.yoo.lms.service;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.repository.TeacherRepository;
import com.yoo.lms.searchCondition.TeacherSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher findById(String id) {
        return teacherRepository.findById(id).get();
    }

    @Transactional
    public void changeAcceptToWaiting(String teacherId) {
        Teacher findTeacher = teacherRepository.findById(teacherId).get();
        findTeacher.changeAcceptToWaiting();
    }

    @Transactional
    public void changeAcceptToReject(String teacherId){
        Teacher findTeacher = teacherRepository.findById(teacherId).get();
        findTeacher.changeAcceptToReject();
    }

    @Transactional
    public void changeAcceptToAccepted(String teacherId){
        Teacher findTeacher = teacherRepository.findById(teacherId).get();
        findTeacher.changeAcceptToAccepted();
    }

    public List<Teacher> searchTeachersBySearchCondition(TeacherSearchCondition searchCondition) {
        return teacherRepository.searchTeachersBySearchCondition(searchCondition);
    }

}
