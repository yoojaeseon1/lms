package com.yoo.lms.service;

import com.yoo.lms.domain.*;
import com.yoo.lms.domain.enumType.BoardType;
import com.yoo.lms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final MemberRepository memberRepository;
    private final HomeworkBoardService homeworkBoardService;
    private final QuestionBoardRepository questionBoardRepository;
    private final CourseMaterialRepository courseMaterialRepository;
    private final InquiryBoardService inquiryBoardService;
    private final String baseDirectory = "C:\\Users\\yoo-pc\\Desktop\\uploadedDirectory\\homeworkBoard\\reply\\";


    @Transactional
    public void saveQuestionReply(String title,
                                  String content,
                                  String memberId,
                                  Long boardId){

        Optional<Member> memberOptional = this.memberRepository.findById(memberId);
        Member member = null;
        if(memberOptional.isPresent())
            member = memberOptional.get();


        Optional<QuestionBoard> questionOptional = questionBoardRepository.findById(boardId);
        QuestionBoard questionBoard = null;
        if(questionOptional.isPresent())
            questionBoard = questionOptional.get();

        BoardReply boardReply = new BoardReply(title, content, member, questionBoard);
        boardReplyRepository.save(boardReply);


        QuestionBoard posting = questionBoardRepository.findPostingById(boardId);
        posting.setReply(boardReply);

    }

    @Transactional
    public void saveInquiryReply(String title,
                                  String content,
                                  String memberId,
                                  Long boardId){

        Optional<Member> memberOptional = this.memberRepository.findById(memberId);
        Member member = null;
        if(memberOptional.isPresent())
            member = memberOptional.get();


        InquiryBoard inquiryBoard = inquiryBoardService.findByIdFetchMember(boardId);

        BoardReply boardReply = new BoardReply(title, content, member, inquiryBoard);
        boardReplyRepository.save(boardReply);


        InquiryBoard posting = inquiryBoardService.findByIdFetchMember(boardId);
        posting.setReply(boardReply);

    }

    @Transactional
    public void saveHomeworkWithFile(String title,
                                     String content,
                                     String studentId,
                                     Long boardId,
                                     MultipartFile[] files) throws IOException {


        Member member = memberRepository.findById(studentId).get();

        HomeworkBoard posting = homeworkBoardService.findPostingById(boardId);

        BoardReply boardReply = new BoardReply(title, content, member, posting);

        boardReplyRepository.save(boardReply);


        if(files[0].getOriginalFilename().equals(""))
            return;

        String savedDirectory = baseDirectory+boardReply.getId() + "\\" + member.getId() + "\\";

        File directory = new File(savedDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            CourseMaterial courseMaterial = new CourseMaterial(boardReply, fileName, savedDirectory);

            courseMaterial.addReply(boardReply);

            courseMaterialRepository.save(courseMaterial);
        }
    }

    public BoardReply findById(Long boardReplyId){

        Optional<BoardReply> boardReplyOptional = boardReplyRepository.findById(boardReplyId);

        if(boardReplyOptional.isPresent())
            return boardReplyOptional.get();
        else
            return null;
    }

    public BoardReply findByBoardIdAndStudentId(Long boardId, String studentId) {
        return boardReplyRepository.findByBoardIdAndStudentId(boardId, studentId);
    }

    public BoardReply findQuestionReplyByBoardId(Long boardId) {
        return boardReplyRepository.findQuestionReplyByBoardId(boardId);
    }

    public List<BoardReply> findHomeworkRepliesByBoardId(Long boardId) {
        return boardReplyRepository.findHomeworkRepliesByBoardId(boardId);
    }

    public BoardReply findInquiryReplyByBoardId(Long boardId) {
        return boardReplyRepository.findInquiryReplyByBoardId(boardId);
    }

    public boolean existBoardReply(Long boardId, String memberId, BoardType boardType) {
        return boardReplyRepository.existBoardReply(boardId, memberId, boardType);
    }

    @Transactional
    public void updateBoardReply(Long boardReplyId,
                                  String studentId,
                                  String title,
                                  String content,
                                  MultipartFile[] files) throws IOException {


        Optional<BoardReply> boardReplyOptional = boardReplyRepository.findById(boardReplyId);

        BoardReply boardReply = null;

        if(boardReplyOptional.isPresent())
            boardReply = boardReplyOptional.get();

        boardReply.updateReply(title, content);

        if(files == null || files[0].getOriginalFilename().equals(""))
            return;

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardReplyId(boardReplyId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists())
                deletedFile.delete();

        }

        courseMaterialRepository.deleteAllByBoardReplyId(boardReplyId);

        String savedDirectory = baseDirectory+boardReply.getId() + "\\" + studentId + "\\";

        File directory = new File(savedDirectory);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(savedDirectory+fileName));

            courseMaterialRepository.save(new CourseMaterial(boardReply, fileName, savedDirectory));
        }

    }

    @Transactional
    public void deleteBoardReply(Long boardReplyId) {

        List<CourseMaterial> materials = courseMaterialRepository.findByBoardReplyId(boardReplyId);

        for (CourseMaterial material : materials) {

            File deletedFile = new File(material.getDirectory()+material.getFilename());

            if(deletedFile.exists()) {
                deletedFile.delete();
            }
        }

        if(materials.size() > 0) {

            File directory = new File(materials.get(0).getDirectory());

            if (directory.exists())
                directory.delete();
        }

        courseMaterialRepository.deleteAllByBoardReplyId(boardReplyId);

        boardReplyRepository.deleteById(boardReplyId);

    }
}
