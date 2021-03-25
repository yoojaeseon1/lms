package com.yoo.lms.service;

import com.yoo.lms.domain.*;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.repository.*;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import com.yoo.lms.searchType.BoardSearchCriteria;
import com.yoo.lms.tools.PageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(readOnly=true)
@Slf4j
public class CourseBoardService {

    private CourseBoardRepository courseBoardRepository;
    private CourseMaterialRepository courseMaterialRepository;
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private EntityManager em;
    private String baseDirectory;


    @Autowired
    public CourseBoardService(CourseBoardRepository courseBoardRepository,
                              CourseRepository courseRepository,
                              CourseMaterialRepository courseMaterialRepository,
                              StudentRepository studentRepository,
                              EntityManager em) {

        this.courseMaterialRepository = courseMaterialRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseBoardRepository = courseBoardRepository;
        this.em = em;
        this.baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\courseBoard\\";
    }


//    @Transactional
//    public void creatCourseBoard(CourseBoard courseBoard) {
//        courseBoardRepository.save(courseBoard);
//    }

    @Transactional
    public void save(Long courseId,
                     String title,
                     String content
    ) {

        Course course = courseRepository.findById(courseId).get();

        //--- session에서 받아와야 함

        Student student = studentRepository.findById("studentId1").get();

        //---

        CourseBoard courseBoard = new CourseBoard(course, title, content, student);

        courseBoardRepository.save(courseBoard);
    }

    @Transactional
    public void saveCourseBoard(MultipartFile[] files,
                             Long courseId,
                             String title,
                             String content,
                                Member member
    ) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        // session에서 받아와야 함--



//        Student student = studentRepository.findById("teacher").get();

        //---

        CourseBoard courseBoard = new CourseBoard(course, title, content, member);

        courseBoardRepository.save(courseBoard);
        em.flush();


        // 파일이 없으면 게시물만 save하고 종료

        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+courseBoard.getId()+"\\";

        File directory = new File(savedDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(courseBoard, fileName, savedDirectory));
        }

    }

    @Transactional
    public void updateCourseBoard(Long boardId,
                               String title,
                               String content,
                               MultipartFile[] files
    ) throws IOException {


        Optional<CourseBoard> courseBoardOptional = courseBoardRepository.findById(boardId);

        CourseBoard findCourseBoard = null;

        if(courseBoardOptional.isPresent())
            findCourseBoard = courseBoardOptional.get();

        findCourseBoard.updateInfo(title, content);

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

        String savedDirectory = baseDirectory+findCourseBoard.getId() + "\\";

        File directory = new File(savedDirectory);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(findCourseBoard, fileName, savedDirectory));
        }

    }

    public CourseBoard findPostingById(Long boardId) {

        CourseBoard courseBoard = courseBoardRepository.findPostingById(boardId);


        return courseBoard;
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
        courseBoardRepository.deletePostingById(boardId);

    }

    public List<BoardListDto> searchPosting(BoardSearchCondition condition, int page, int size) {
        return courseBoardRepository.searchPosting(condition, page, size);
    }

    public long countTotalPosting(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        return courseBoardRepository.countTotalPosting(condition, page, size, numCurrentPageContent);
    }

    public List<BoardListDto> searchPostingAllCriteria(BoardSearchCondition condition, int page, int size) {

        List<BoardListDto> boardListDtos = courseBoardRepository.searchPostingAllCriteria(condition, page, size);

        for (BoardListDto boardListDto : boardListDtos) {
            log.info("boardListDto.getTitle() : " + boardListDto.getTitle());
        }

        return boardListDtos;
    }

    public long countTotalAllCriteria(BoardSearchCondition condition, int page, int size, int numCurrentPageContent) {
        return courseBoardRepository.countTotalAllCriteria(condition, page, size, numCurrentPageContent);
    }

    public PageMaker makePageMaker(int page, long totalCount, BoardSearchCriteria searchCriteria) {

        return new PageMaker(page, totalCount, searchCriteria);

    }



}
