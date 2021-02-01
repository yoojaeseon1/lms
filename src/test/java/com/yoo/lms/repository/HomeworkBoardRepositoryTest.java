package com.yoo.lms.repository;

import com.yoo.lms.domain.Course;
import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.QuestionBoard;
import com.yoo.lms.domain.Student;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class HomeworkBoardRepositoryTest {

    @Autowired
    HomeworkBoardRepository homeworkBoardRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Test
    public void searchByDynamic(){

        //given
        String title = "homeworkBoardTitle1";
        String content = null;
        Long courseId = 1L;

        BoardSearchCondition condition = new BoardSearchCondition(courseId, title, content, null, null);

        int page = 0;
        int size = 10;

        //when

        List<BoardListDto> contents = homeworkBoardRepository.searchPosting(condition, page, size);
        long totalCount = homeworkBoardRepository.countTotalPosting(condition, page, size, contents.size());

        //then

        assertThat(contents.get(0).getCreatedBy()).isEqualTo("teacher19");
        assertThat(totalCount).isEqualTo(11);

    }



}