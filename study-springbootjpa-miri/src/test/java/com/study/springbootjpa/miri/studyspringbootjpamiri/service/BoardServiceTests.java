package com.study.springbootjpa.miri.studyspringbootjpamiri.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;
import com.study.springbootjpa.miri.service.BoardServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import lombok.extern.log4j.Log4j2;
/** 유효성 검사 제외하고 가장 기본적인 CRUD 를 확인하는 케이스모음.
 * - testInsert
 * - testUpdate
 * - testDelete
 * - testGet
 * - testGetList
 * 
 * [참고]
 * https://beomseok95.tistory.com/205
 */
// @Disabled
@Log4j2
@SpringBootTest(classes = StudySpringbootjpaMiriApplication.class) // 이거 없으면 bean 을 못찾음. 찾아볼것.
public class BoardServiceTests {
    
    @Autowired
    private BoardServiceImpl service;
    
    @Autowired
    private BoardRepository repository;
    
    
    @Test
    public void test(){
        log.info("hello world");
        log.info("service.getClass(): "+service.getClass());
    }
    
    @Test
    public void testInsert2(){
        
        String i = "abcd";
        
        // 스트림 for문
        Board entity = Board.builder()
        // .board_id()
        .title("title..."+i)
        .content("content...")
        .writer("writer"+i)
        .password("pw"+i)
        .createdAt(LocalDateTime.now())
        .modifiedAt(LocalDateTime.now())
        .build();
        
        // 어느 시점에서 ID 가 있고, 없는지 테스트.
        log.info("Board entity 빌더로 초기화했을 때 ID: "+entity.getBoard_id()); // 출력 null
        log.info(entity.getClass().getName());
        
        Board entityAfterSave = repository.save(entity);
        log.info("repository.save 후 반환된 Board ID: "+entityAfterSave.getBoard_id()); // 출력 ID
        log.info(entityAfterSave.getClass().getName());
        
        
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
            .replys(new ArrayList<>())
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
        
        // id 6번 게시물 가져오기
        Long num = 6L;
        
        BoardDTO board = service.read(num);
        log.info("get으로 가져온 board: "+board.toString());
        
        // 가정한 id 게시물 VS 실제 가져와진 게시물 id 비교하기
        assertTrue(num == board.getBoard_id(), "testGet 테스트 : 성공");
        
        
    }
    
    @Transactional
    @Test
    public void testUpdate(){
        // target번 게시물의 제목과 내용을 한글로 변경한다.
        Long target = 13L;
        
        BoardDTO oldBoard = service.read(target); // 기존 게시물
        String newTitle = target+"번 제목입니다.";
        String newContent = target+"번 내용입니다.";
        oldBoard.setTitle(newTitle);
        oldBoard.setContent(newContent);
        
        BoardDTO newBoard = service.update(oldBoard);
        
        // 새로 업데이트한 게시물의 필드가 의도에 맞게 변경되었는지 확인
        assertEquals(newBoard.getTitle(), newTitle);
        assertEquals(newBoard.getContent(), newContent);

        assertTrue(service.read(target).getBoard_id()==newBoard.getBoard_id(), "testUpdate 테스트 : 성공");

    }

    /**
     * 
     */
    @Transactional
    @Test
    public void testDelete(){
        // target번 게시물을 삭제한다.
        Long target = 9L;

        boolean successed = service.delete(target); // 실제로 삭제된 대상

        assertTrue(successed, "testDelete 테스트 : 성공");

        // TODO : 리플 서비스 테스트 후, 여기로 다시 돌아와서 리플도 삭제됬는지 확인

    }

    @Test
    public void testGetList(){
        // 모든 게시물 가져온다(페이징X)
        service.getList().forEach(i->{
            log.info(i.toString());
        });

    }
    
}