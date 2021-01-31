package com.yoo.lms.service;

import com.yoo.lms.domain.CounselBoard;
import com.yoo.lms.domain.CourseBoard;
import com.yoo.lms.repository.CounselBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CounselBoardService {

    private final CounselBoardRepository counselBoardRepository;

    public void save(CounselBoard counselBoard){
        counselBoardRepository.save(counselBoard);
    }

    public CounselBoard findOne(Long id) {
        return counselBoardRepository.findById(id).get();
    }

    public void deleteOne(Long id) {
        counselBoardRepository.deleteById(id);
    }

    public void updateOne(CounselBoard updated) {
        CounselBoard findPosting = counselBoardRepository.findById(updated.getId()).get();

        findPosting.updateInfo(updated);
    }






}
