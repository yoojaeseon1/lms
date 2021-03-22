package com.yoo.lms.service;

import com.yoo.lms.domain.Student;
import com.yoo.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student findById(String id) {
        return studentRepository.findById(id).get();
    }




}
