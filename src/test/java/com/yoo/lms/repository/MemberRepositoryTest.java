package com.yoo.lms.repository;

import com.yoo.lms.domain.Member;
import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
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


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;



    @Test
    public void joinTest(){

        Student student1 = new Student("yoo1",
                "1234",
                "name1",
                "email",
                new Address("1","2","3"),
                LocalDate.now(),
                MemberType.STUDENT);

        Teacher teacher = new Teacher("yoo2",
                "1234",
                "name2",
                "email",
                new Address("1","2","3"),
                LocalDate.now(),
                MemberType.TEACHER);

        memberRepository.save(student1);
        memberRepository.save(teacher);

    }

    @Test
    public void searchMember(){

        //given

//        MemberSearchCondition searchCondition = new MemberSearchCondition("studentId1", null, null, null);
        MemberSearchCondition searchCondition = new MemberSearchCondition("studentId1", "1234", null, null);

        //when

        Member member = memberRepository.searchMember(searchCondition);


        //then

//        assertThat(member.getName()).isEqualTo("studentName1");
        assertThat(member).isNotNull();

    }

    @Test
    public void updateInfo(){

//        Member findMember = memberRepository.findById("yoo2").get();
//
//        Teacher teacher = new Teacher("yoo2", "1234", "name3", 12, new Address("1","2","3"));
//
//        findMember.updateInfo(teacher);


    }

}