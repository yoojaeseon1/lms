package com.yoo.lms.service;

import com.yoo.lms.domain.*;
import com.yoo.lms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
public class BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final HomeworkBoardService homeworkBoardService;
    private final QuestionBoardRepository questionBoardRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final EntityManager em;
    private final String baseDirectory;

    public BoardReplyService(BoardReplyRepository boardReplyRepository,
                             TeacherRepository teacherRepository,
                             StudentRepository studentRepository,
                             HomeworkBoardService homeworkBoardService,
                             QuestionBoardRepository questionBoardRepository,
                             CourseMaterialRepository courseMaterialRepository,
                             EntityManager em) {

        this.boardReplyRepository = boardReplyRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.homeworkBoardService = homeworkBoardService;
        this.questionBoardRepository = questionBoardRepository;
        this.em = em;
        this.courseMaterialRepository = courseMaterialRepository;
        this.baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\homeworkBoard\\reply\\";
    }

    @Transactional
    public void save(String title,
                     String content,
                     String memberId,
                     Long boardId){
//                     ,Long courseId) {

//        Optional<Course> courseOptional = courseRepository.findById(courseId);
//        Course course = null;
//        if(courseOptional.isPresent())
//            course = courseOptional.get();

        Optional<Teacher> teacherOptional = teacherRepository.findById(memberId);
        Teacher teacher = null;
        if(teacherOptional.isPresent())
            teacher = teacherOptional.get();


        Optional<QuestionBoard> questionOptional = questionBoardRepository.findById(boardId);
        QuestionBoard questionBoard = null;
        if(questionOptional.isPresent())
            questionBoard = questionOptional.get();

        boardReplyRepository.save(new BoardReply(title, content, teacher, questionBoard));
        log.info("BoardReplyService.save end==============");

    }

    public void saveWithFile(String title,
                             String content,
                             Long boardId,
                             MultipartFile[] files
                             ) throws IOException {


        // session에서 id를 가져와야 함
        Student student = studentRepository.findById("studentId1").get();

        HomeworkBoard posting = homeworkBoardService.findPostingById(boardId, false);

        BoardReply boardReply = new BoardReply(title, content, student, posting);

        boardReplyRepository.save(boardReply);

        em.flush();


        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+student.getId()+"\\";

        File directory = new File(savedDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            CourseMaterial courseMaterial = new CourseMaterial(fileName, savedDirectory, boardReply);

            courseMaterial.addReply(boardReply);

            courseMaterialRepository.save(courseMaterial);
        }
    }
}
