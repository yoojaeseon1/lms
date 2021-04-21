package com.yoo.lms.service;

import com.yoo.lms.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardReplyServiceTest {

    @Autowired
    BoardReplyService boardReplyService;

    @Test
    public void mapTest(){

        //given

//        JpaRepository repository = boardReplyService.getRepositoryMap().get("teacher");
//
//        repository = (JpaRepository<Teacher, Long>) repository;

        //when

//        JpaRepository<Teacher, Long> teacherId1 = (JpaRepository<Teacher, Long>) repository.findById("teacherId1");

        //then

    }

}