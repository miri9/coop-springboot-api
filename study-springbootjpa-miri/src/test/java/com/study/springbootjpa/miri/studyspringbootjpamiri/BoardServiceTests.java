package com.study.springbootjpa.miri.studyspringbootjpamiri;


import java.time.LocalDateTime;
import java.util.stream.IntStream;

import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.service.BoardServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;
/** 유효성 검사 제외하고 가장 기본적인 CRUD 를 확인하는 케이스모음.
 * - testInsert
 * - testUpdate
 * - testDelete
 * - testGet
 * - testGetList
 */
@Log4j2
@SpringBootTest(classes = StudySpringbootjpaMiriApplication.class) // 이거 없으면 bean 을 못찾음. 찾아볼것.
public class BoardServiceTests {

    // @Autowired
    // private BoardServiceImpl service;
    @Autowired
    private BoardServiceImpl service;


    @Test
    public void test(){
        log.info("hello world");
        log.info("service.getClass(): "+service.getClass());
    }

    @Test
    public void testInsert(){
        // 13개의 게시글 insert
        int num = 13;

        // 스트림 for문
        IntStream.range(0, num).forEach( i -> {
            BoardDTO dto = BoardDTO.builder()
                            // .board_id()
                            .title("title..."+i)
                            .content("content...")
                            .writer("writer"+i)
                            .password("pw"+i)
                            .createdAt(LocalDateTime.now())
                            .modifiedAt(LocalDateTime.now())
                            .build();
            
            // board 로 전환 후 save : save 안하고 필드 log만 찍기
            service.insert(dto);
        });
    }
    
    @Test
    public void testGet(){
        /**
         * org.hibernate.LazyInitializationException:
         * failed to lazily initialize a collection of role:
         * com.study.springbootjpa.miri.domain.Board.replys,
         * could not initialize proxy - no Session
         */
        // id 6번 가져오기
        Long num = 6L;

        log.info(service.read(num).toString());
    }
    @Test
    public void testUpdate(){
        // 

    }
    @Test
    public void testDelete(){

    }
    @Test
    public void testGetList(){

    }
    
}
