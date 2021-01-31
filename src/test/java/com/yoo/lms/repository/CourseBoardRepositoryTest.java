package com.yoo.lms.repository;

import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourseBoardRepositoryTest {

    @Autowired
    CourseBoardRepository courseBoardRepository;

    @Test
    public void searchByAllCategories(){

        //given

        String title = "title1";
        Long courseId = 1L;

        BoardSearchCondition condition = new BoardSearchCondition(courseId, title, null, null, null);

        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when

        List<BoardListDto> content = courseBoardRepository.searchPosting(condition, page, size);
        long totalCount = courseBoardRepository.countTotalPosting(condition, page, size, content.size());

//        List<BoardListDto> content = courseBoardRepository.searchByAllCriteria(keyword, page, size);
//        long totalCount = courseBoardRepository.countTotalByAllCriteria(keyword, page, size, content.size());


        System.out.println("==========");
        for (BoardListDto boardListDto : content) {
            System.out.println("boardListDto.getTitle() = " + boardListDto.getTitle());
        }
        System.out.println("==========");
        
        //then

        assertThat(content.get(0).getTitle()).isEqualTo("title19");
        assertThat(totalCount).isEqualTo(11);



    }

}