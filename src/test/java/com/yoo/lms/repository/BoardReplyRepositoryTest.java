package com.yoo.lms.repository;

import com.yoo.lms.domain.BoardReply;
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
class BoardReplyRepositoryTest {

    @Autowired
    BoardReplyRepository boardReplyRepository;

    @Test
    public void findHomeworkRepliesByBoardId(){

        //given

        Long boardId = 190L;

        //when
        List<BoardReply> replies = boardReplyRepository.findHomeworkRepliesByBoardId(boardId);


        //then

        assertThat(replies.size()).isEqualTo(20);

    }

}