package com.yoo.lms.service;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import com.yoo.lms.repository.MemberRepository;
import com.yoo.lms.searchCondition.MemberSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void createTempPW(){

        //given
//        String tempPW = memberService.createTempPW(8);
//        System.out.println("tempPW = " + tempPW);
        //when


        //then

    }

    @Test
    public void updateTempPW(){

        //given

        String id = "tempId";
        String name = "tempName";
        String password = "1234";
        String email = "you8054@nate.com";


        Student student = new Student(
                id,
                password,
                name,
                email,
                new Address("1","2","3"),
                LocalDate.now(),
                MemberType.STUDENT);

        memberService.joinStduent(student);

        em.flush();

        MemberSearchCondition searchCondition = new MemberSearchCondition(id, null, name, email);

        //when


        boolean updateResult = memberService.updateTempPW(searchCondition);

        em.flush();

        searchCondition = new MemberSearchCondition("tempId", null,null, null);
//        MemberSearchCondition searchCondition = new MemberSearchCondition("tempId");

        Member member = memberRepository.searchMember(searchCondition);

        //then

        assertThat(updateResult).isTrue();
        assertThat(member.getPassword()).isNotEqualTo(password);


    }

    @Test
    public void create(){

        //given


        //when


        //then

    }

    @Test
    public void testDownCasting(){

        //given

        String id = "tempId";
        String name = "tempName";
        String password = "1234";
        String email = "you8054@nate.com";

        Member member = new Member(
                id,
                password,
                name,
                email,
                new Address("1","2","3"),
                LocalDate.now(),
                MemberType.STUDENT);

        //when

        //then

    }

}