package com.yoo.lms.service;

import com.yoo.lms.domain.Teacher;
import com.yoo.lms.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher findById(String id) {
        return teacherRepository.findById(id).get();
    }

}
