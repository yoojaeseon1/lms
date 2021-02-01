package com.yoo.lms.service;


import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.domain.Student;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.CourseMaterialRepository;
import com.yoo.lms.repository.CourseRepository;
import com.yoo.lms.repository.QuestionBoardRepository;
import com.yoo.lms.repository.StudentRepository;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.tools.PageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class QuestionBoardService {


    private QuestionBoardRepository questionBoardRepository;
    private CourseMaterialRepository courseMaterialRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private EntityManager em;

    private final String savedDirectory;

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
        savedDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\";
    }

    @Transactional
//    public void save(MultipartFile[] files) throws IOException {
    public void save(Long courseId,
                     String title,
                     String content,
                     MultipartFile[] files) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        // session에서 받아와야 함--

        Student student = studentRepository.findById("studentId1").get();

        //---

        QuestionBoard questionBoard = new QuestionBoard(course, title, content, student);

        questionBoardRepository.save(questionBoard);
        em.flush();

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(questionBoard, fileName, savedDirectory));
        }

//        questionBoardRepository.save(questionBoard);
//        courseMaterialRepository.save(courseMaterial);
    }

    public QuestionBoard findById(Long boardId) {

        Optional<QuestionBoard> questionOptional = questionBoardRepository.findById(boardId);

        QuestionBoard findBoard = null;

        if(questionOptional.isPresent())
            findBoard = questionOptional.get();

        return findBoard;
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
