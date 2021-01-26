package com.yoo.lms.service;

import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.repository.CourseBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CourseBoardService {

    private final CourseBoardRepository courseBoardRepository;


    @Transactional
    public void creatCourseBoard(CourseBoard courseBoard) {
        courseBoardRepository.save(courseBoard);
    }



}
