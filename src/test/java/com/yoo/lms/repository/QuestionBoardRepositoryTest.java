package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.domain.Student;
import com.yoo.lms.dto.BoardListDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class QuestionBoardRepositoryTest {

    @Autowired
    QuestionBoardRepository questionBoardRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EntityManager em;

//    @Test
//    public void searchByAllCriteria(){
//
//        //given
//
//        int page = 0;
//        int size = 10;
//
//        String keyword = "student1";
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        //when
//
//        List<BoardListDto> content = questionBoardRepository.searchByAllCriteria(keyword, page, size);
//
//        long totalCount = questionBoardRepository.countTotalByAllCriteria(keyword, page, size, content.size());
//
//
//        //then
//
//        assertThat(content.get(0).getTitle()).isEqualTo("QBoardTitle19");
//        assertThat(totalCount).isEqualTo(11);
//
//    }
//
//    @Test
//    public void searchAll(){
//
//        //given
//        int page = 0;
//        int size = 10;
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        //when
//
//        List<BoardListDto> content = questionBoardRepository.searchAll(page, size);
//
//        long totalCount = questionBoardRepository.countTotalAll(page, size, content.size());
//
//        //then
//
//        assertThat(content.size()).isEqualTo(10);
//        assertThat(totalCount).isEqualTo(50);
//
//    }

@Test
public void createPosting(){

    //given

    Course course = courseRepository.findById(2L).get();

    Student student = studentRepository.findById("studentId1").get();

    QuestionBoard board = new QuestionBoard(course, "title", "content", student);

    //when

    System.out.println("board.getId() = " + board.getId());

    questionBoardRepository.save(board);

    em.flush();

    System.out.println("board.getId() = " + board.getId());

    //then

}

}