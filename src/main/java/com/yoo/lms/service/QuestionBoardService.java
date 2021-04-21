package com.yoo.lms.service;


import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.*;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class QuestionBoardService {


    private final QuestionBoardRepository questionBoardRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseRepository courseRepository;
    private final ViewRepository viewRepository;
    private final String baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\questionBoard\\";;


    @Transactional
    public void saveQuestion(MultipartFile[] files,
                     Long courseId,
                     String title,
                     String content,
                     Member member) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        QuestionBoard questionBoard = new QuestionBoard(course, title, content, member);

        questionBoardRepository.save(questionBoard);

        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+questionBoard.getId()+"\\";

        File directory = new File(savedDirectory);

        if(!directory.exists())
            directory.mkdirs();


        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(questionBoard, fileName, savedDirectory));
        }
    }

    public QuestionBoard findPostingById(Long boardId) {
        return questionBoardRepository.findByIdFetchMember(boardId);
    }

    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, int page, int size) {
        return questionBoardRepository.searchPosting(condition, isMultipleCriteria, PageRequest.of(page, size));
    }

    @Transactional
    public void addViewCount(Long boardId){
        QuestionBoard questionBoard = questionBoardRepository.findById(boardId).get();

        View view = new View(questionBoard);
        viewRepository.save(view);
        view.addViewCount(questionBoard);
    }

    @Transactional
    public void updateQuestion(Long boardId,
                                  String title,
                                  String content,
                                  MultipartFile[] files
                                  ) throws IOException {


        Optional<QuestionBoard> questionBoardOptional = questionBoardRepository.findById(boardId);

        QuestionBoard findQuestion = null;
        if(questionBoardOptional.isPresent())
            findQuestion = questionBoardOptional.get();

        findQuestion.updateInfo(title, content);

        if(files[0].getOriginalFilename().equals(""))
            return;

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists())
                deletedFile.delete();

        }

        courseMaterialRepository.deleteAllByBoardId(boardId);

        String savedDirectory = baseDirectory+findQuestion.getId() + "\\";

        File directory = new File(savedDirectory);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(findQuestion, fileName, savedDirectory));
        }

    }



    @Transactional
    public void deleteByBoardId(Long boardId) {

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists()) {
                deletedFile.delete();
            }
        }

        if(materials.size() > 0) {
           File directory = new File(materials.get(0).getDirectory());

            if (directory != null && directory.exists())
                directory.delete();
        }

        viewRepository.deleteAllByBoardId(boardId);

        courseMaterialRepository.deleteAllByBoardId(boardId);
        questionBoardRepository.deleteById(boardId);
    }


}
