package com.yoo.lms.repository;

import com.yoo.lms.domain.Student;
import com.yoo.lms.domain.Teacher;
import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;


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

        Student student1 = new Student("yoo1", "1234", "name1", 12, new Address("1","2","3"), LocalDate.now(), MemberType.STUDENT);
        Teacher teacher = new Teacher("yoo2", "1234", "name2", 12, new Address("1","2","3"), LocalDate.now(), MemberType.TEACHER);

        memberRepository.save(student1);
        memberRepository.save(teacher);

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