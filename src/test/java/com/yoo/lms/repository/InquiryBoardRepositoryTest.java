package com.yoo.lms.repository;

import com.yoo.lms.domain.InquiryBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InquiryBoardRepositoryTest {

    @Autowired
    InquiryBoardRepository inquiryBoardRepository;

    @Test
    public void createModifiedDateTest(){

        //given


        //when

//        List<InquiryBoard> boards = inquiryBoardRepository.findAll();


        //then
//        for (InquiryBoard board : boards) {
//            assertThat(board.getCreatedDate()).isEqualTo(board.getLastModifiedDate());
//        }


    }

}