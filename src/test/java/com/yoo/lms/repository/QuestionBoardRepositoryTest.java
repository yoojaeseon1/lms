package com.yoo.lms.repository;

import com.yoo.lms.dto.BoardListDto;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class QuestionBoardRepositoryTest {

    @Autowired
    QuestionBoardRepository questionBoardRepository;

    @Test
    public void searchByAllCriteria(){

        //given

        String keyword = "QBoardContent1";
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when

        List<BoardListDto> content = questionBoardRepository.searchByAllCriteria(keyword, pageRequest);

        long totalCount = questionBoardRepository.countTotalByAllCriteria(keyword);


        //then

        assertThat(content.get(0).getTitle()).isEqualTo("QBoardTitle19");
        assertThat(totalCount).isEqualTo(11);

    }

}