package com.yoo.lms.service;

import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.repository.CourseMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseMaterialService {


    private final CourseMaterialRepository courseMaterialRepository;

    public List<CourseMaterial> findByBoardId(Long boardId){
        return courseMaterialRepository.findByBoardId(boardId);
    }

    public List<CourseMaterial> findByBoardReplyId(Long boardReplyId) {
        return courseMaterialRepository.findByBoardReplyId(boardReplyId);
    }

}
