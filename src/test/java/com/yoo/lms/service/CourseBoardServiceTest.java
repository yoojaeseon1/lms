package com.yoo.lms.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourseBoardServiceTest {

    @Test
    public void test(){

        //given

        int page = 0;
        String searchType = null;
        String keyword = "testKeyword";

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("searchType", searchType)
                .queryParam("keyword" , keyword)
                .build();

        //when
        System.out.println("================");
        System.out.println(uriComponents.toString());
        System.out.println("================");

        //then

    }

}