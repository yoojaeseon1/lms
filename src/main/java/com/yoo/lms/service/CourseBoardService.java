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
@Transactional(readOnly=true)
@Slf4j
public class CourseBoardService {

    private final CourseBoardRepository courseBoardRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseRepository courseRepository;
    private final ViewRepository viewRepository;
    private final String baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\courseBoard\\";


    @Transactional
    public void saveCourseBoard(MultipartFile[] files,
                                Long courseId,
                                String title,
                                String content,
                                Member member) throws IOException {

        Course course = courseRepository.findById(courseId).get();

        CourseBoard courseBoard = new CourseBoard(course, title, content, member);

        courseBoardRepository.save(courseBoard);

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

    public Page<BoardListDto> searchPosting(BoardSearchCondition condition, boolean isMultipleCriteria, int page, int size) {
        return courseBoardRepository.searchPosting(condition, isMultipleCriteria, PageRequest.of(page, size));
    }

    public CourseBoard findPostingById(Long boardId) {
        return courseBoardRepository.findByIdFetchMember(boardId);
    }


    @Transactional
    public void addViewCount(Long boardId){
        CourseBoard courseBoard = courseBoardRepository.findById(boardId).get();
        View view = new View(courseBoard);

        viewRepository.save(view);

        view.addViewCount(courseBoard);
    }

    @Transactional
    public void updateCourseBoard(Long boardId,
                               String title,
                               String content,
                               MultipartFile[] files) throws IOException {


        Optional<CourseBoard> courseBoardOptional = courseBoardRepository.findById(boardId);

        CourseBoard findCourseBoard = null;

        if(courseBoardOptional.isPresent())
            findCourseBoard = courseBoardOptional.get();

        findCourseBoard.updateInfo(title, content);

        if(files[0].getOriginalFilename().equals(""))
            return;

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists())
                deletedFile.delete();

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


    @Transactional
    public void deleteByBoardId(Long boardId) {

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardId(boardId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists()) {
                deletedFile.delete();
            }
        }

        if(materials.size()>0) {
            File directory = new File(materials.get(0).getDirectory());

            if (directory.exists())
                directory.delete();
        }

        courseMaterialRepository.deleteAllByBoardId(boardId);
        viewRepository.deleteAllByBoardId(boardId);
        courseBoardRepository.deleteById(boardId);
    }



}
