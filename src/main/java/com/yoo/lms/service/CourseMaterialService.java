package com.yoo.lms.service;

import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.repository.CourseMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseMaterialService {

    @Autowired
    CourseMaterialRepository courseMaterialRepository;

    public List<CourseMaterial> findByBoardId(Long boardId){
        return courseMaterialRepository.findByBoardId(boardId);
    }

    public List<String> parseFileName(List<CourseMaterial> materials) {

        List<String> fileNames = new ArrayList<>();

        for(CourseMaterial material : materials) {
            String fileName = material.getFilename();
            fileNames.add(fileName.substring(fileName.indexOf('_')+1));
        }
        return fileNames;
    }

}
