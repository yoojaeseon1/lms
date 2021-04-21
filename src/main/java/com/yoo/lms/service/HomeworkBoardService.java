package com.yoo.lms.service;

import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.*;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.tools.PageMaker;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HomeworkBoardService {

    private final HomeworkBoardRepository homeworkBoardRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseRepository courseRepository;
    private final ViewRepository viewRepository;
    private final String baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\homeworkBoard\\";


    @Transactional
    public void saveHomework(MultipartFile[] files,
                             Long courseId,
                             String title,
                             String content,
                             Member member) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        HomeworkBoard homeworkBoard = new HomeworkBoard(course, title, content, member);

        homeworkBoardRepository.save(homeworkBoard);


        // 파일이 없으면 게시물만 save하고 종료

        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+homeworkBoard.getId()+"\\";

        File directory = new File(savedDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(homeworkBoard, fileName, savedDirectory));
        }
    }

    @Transactional
    public void addViewCount(Long boardId){
        HomeworkBoard homeworkBoard = homeworkBoardRepository.findById(boardId).get();

        View view = new View(homeworkBoard);

        viewRepository.save(view);
        view.addViewCount(homeworkBoard);

    }

    @Transactional
    public void updateHomework(Long boardId,
                               String title,
                               String content,
                               MultipartFile[] files) throws IOException {


        Optional<HomeworkBoard> homeworkBoardOptional = homeworkBoardRepository.findById(boardId);

        HomeworkBoard homeworkBoard = null;
        if(homeworkBoardOptional.isPresent())
            homeworkBoard = homeworkBoardOptional.get();

        homeworkBoard.updateInfo(title, content);

        // 파일이 없으면 게시물만 update하고 종료

        if(files[0].getOriginalFilename().equals(""))
            return;

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists())
                deletedFile.delete();

        }

        courseMaterialRepository.deleteAllByBoardId(boardId);

        String savedDirectory = baseDirectory+homeworkBoard.getId() + "\\";

        File directory = new File(savedDirectory);
        if(!directory.exists())
            directory.mkdirs();


        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(homeworkBoard, fileName, savedDirectory));
        }
    }

    public HomeworkBoard findPostingById(Long boardId, boolean hasReplies) {

        HomeworkBoard homeworkBoard = homeworkBoardRepository.findPostingById(boardId, hasReplies);

        return homeworkBoard;
    }

    @Transactional
    public void deleteByBoardId(Long boardId) {

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists())
                deletedFile.delete();

        }

        if(materials.size() > 0) {

            File directory = new File(materials.get(0).getDirectory());

            if (directory.exists())
                directory.delete();
        }

        viewRepository.deleteAllByBoardId(boardId);

        courseMaterialRepository.deleteAllByBoardId(boardId);
        homeworkBoardRepository.deleteById(boardId);
    }

    public String findTitleByBoardId(Long boardId) {
        return homeworkBoardRepository.findTitleByBoardId(boardId);
    }

    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, int page, int size) {
        return homeworkBoardRepository.searchPosting(condition, isMultipleCriteria, PageRequest.of(page, size));
    }
}
