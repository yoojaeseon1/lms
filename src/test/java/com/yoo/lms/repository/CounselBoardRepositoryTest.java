package com.yoo.lms.repository;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.dto.BoardListDto;
import com.yoo.lms.searchCondition.BoardSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CounselBoardRepositoryTest {

    @Autowired
    CounselBoardRepository counselBoardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void searchByDynamicCondition(){

        //given
        String title = null;
        String content = null;
        String memberId = null;
        MemberType memberType = MemberType.TEACHER;

        int page = 0;
        int size = 10;

        BoardSearchCondition condition = new BoardSearchCondition(title, content, memberId, memberType);

        //when

        List<BoardListDto> contents = counselBoardRepository.searchByDynamic(condition, page, size);
        long totalCount = counselBoardRepository.countTotalByDynamic(condition, page, size, contents.size());

        //then

        assertThat(contents.get(0).getTitle()).isEqualTo("counselBoardTitle50");
        assertThat(totalCount).isEqualTo(50);

    }

}