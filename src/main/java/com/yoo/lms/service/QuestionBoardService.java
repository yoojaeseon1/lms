package com.yoo.lms.service;


import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.CourseMaterialRepository;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.QuestionBoardRepository;
import com.yoo.lms.repository.StudentRepository;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.tools.PageMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
public class QuestionBoardService {


    private QuestionBoardRepository questionBoardRepository;
    private CourseMaterialRepository courseMaterialRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private EntityManager em;
    private String baseDirectory;


    @Autowired
    public QuestionBoardService(QuestionBoardRepository questionBoardRepository,
                                CourseMaterialRepository courseMaterialRepository,
                                CourseRepository courseRepository,
                                StudentRepository studentRepository,
                                EntityManager em) {

        this.questionBoardRepository = questionBoardRepository;
        this.courseMaterialRepository = courseMaterialRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.em = em;
        this.baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\questionBoard\\";
    }

    @Transactional
    public void save(Long courseId,
                     String title,
                     String content
                     ) {

        Course course = courseRepository.findById(courseId).get();

        //--- session에서 받아와야 함

        Student student = studentRepository.findById("studentId1").get();

        //---

        QuestionBoard questionBoard = new QuestionBoard(course, title, content, student);

        questionBoardRepository.save(questionBoard);
    }

    @Transactional
    public void saveQuestion(MultipartFile[] files,
                     Long courseId,
                     String title,
                     String content,
                     Member member
    ) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        // session에서 받아와야 함--

//        Student student = studentRepository.findById("studentId1").get();

        //---

        QuestionBoard questionBoard = new QuestionBoard(course, title, content, member);

        questionBoardRepository.save(questionBoard);
        em.flush();


        // 파일이 없으면 게시물만 save하고 종료
        
        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+questionBoard.getId()+"\\";

        File directory = new File(savedDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(questionBoard, fileName, savedDirectory));
        }

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

        // 파일이 없으면 게시물만 update하고 종료

        if(files[0].getOriginalFilename().equals(""))
            return;

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists()) {
                log.info("file exist!!!");
                deletedFile.delete();
            }
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

    public QuestionBoard findPostingById(Long boardId) {

        QuestionBoard questionBoard = questionBoardRepository.findPostingById(boardId);

        return questionBoard;
    }

    public void deleteByBoardId(Long boardId) {

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists()) {
                deletedFile.delete();
            }
        }

        File directory = new File(materials.get(0).getDirectory());

        if(directory.exists())
            directory.delete();

        courseMaterialRepository.deleteAllByBoardId(boardId);
        questionBoardRepository.deletePostingById(boardId);

    }

    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {
        return questionBoardRepository.searchPosting(condition, page, size);
    }

    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        return questionBoardRepository.countTotalPosting(condition, page, size, numCurrentPageContent);
    }

    public List<BoardListDto> searchPostingAllCriteria(BoardSearchCondition condition, int page, int size) {
        return questionBoardRepository.searchPostingAllCriteria(condition, page, size);
    }

    public long countTotalAllCriteria(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        return questionBoardRepository.countTotalAllCriteria(condition, page, size, numCurrentPageContent);
    }

    public PageMaker makePageMaker(int page, long totalCount, BoardSearchCriteria searchCriteria) {

        return new PageMaker(page, totalCount, searchCriteria);

    }



}
