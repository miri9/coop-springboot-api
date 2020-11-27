package com.study.springbootjpa.miri.studyspringbootjpamiri.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;
import com.study.springbootjpa.miri.service.BoardServiceImpl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.extern.log4j.Log4j2;
/** 유효성 검사 제외하고 가장 기본적인 CRUD 를 확인하는 케이스모음.
 * - testInsert
 * - testUpdate
 * - testDelete
 * - testGet
 * - testGetList (페이징X)
 * - testGetList2 (페이징O)
 * 
 * [참고]
 * https://beomseok95.tistory.com/205
 * https://sas-study.tistory.com/316 assertAll
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
    public void aaaaq(){
        a tmp = new a(10);
        log.info("tmp.z(): "+tmp.z());
        a tmp2 = new a(10);
        log.info("tmp.z(): "+tmp2.z());

    }

    @Test
    public void tmpTest(){
        //서비스단
        int page = 0;
        int amount = 10;

        Sort sort = Sort.by(Direction.DESC, "boardId"); // board 내림차순
        Pageable pageable = PageRequest.of(page, amount, sort);

        Page<Board> results = repository.getBoardListWithReply(pageable);

        results.forEach(i -> log.info("========= boardId: "+i.getBoardId()));

    }

    @Test
    public void test(){
        log.info("hello world");
        log.info("service.getClass(): "+service.getClass());
    }
    
    @Test
    public void testAAAA(){
        // 85개가 있다고 가정하고, 게시물 가져오기.
        List<Board> boards = repository.getBoardListWithReply();

        // 카운트 세기
        assertTrue(boards.size()==85);

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
        log.info("Board entity 빌더로 초기화했을 때 ID: "+entity.getBoardId()); // 출력 null
        log.info(entity.getClass().getName());
        
        Board entityAfterSave = repository.save(entity);
        log.info("repository.save 후 반환된 Board ID: "+entityAfterSave.getBoardId()); // 출력 ID
        log.info(entityAfterSave.getClass().getName());
        
        
    }
    
    @Test
    public void testInsert(){
        // num개의 게시글 insert
        int num = 13;
        
        // 스트림 for문
        IntStream.range(0, num).forEach( i -> {
            BoardDTO dto = BoardDTO.builder()
            .title("title..."+i)
            .content("content...")
            .writer("writer"+i)
            .password("pw"+i)
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .replys(new ArrayList<>())
            .build();
            
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
        Long num = 8L;
        
        BoardDTO board = service.read(num);
        log.info("get으로 가져온 board: "+board.toString());
        
        // 가정한 id 게시물 VS 실제 가져와진 게시물 id 비교하기
        assertTrue(num == board.getBoardId(), "testGet 테스트 : 게시글 id 불일치");
        
        
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

        assertTrue(service.read(target).getBoardId()==newBoard.getBoardId(), "testUpdate 테스트 : 게시글 id 불일치");

    }

    /**
     * 
     */
    @Transactional
    @Test
    public void testDelete(){
        // target번 게시물을 삭제한다.
        Long target = 5L;

        BoardDTO deletedBoard = service.delete(target); // 실제로 삭제된 대상

        assertTrue(deletedBoard.isDeleted(), "testDelete 테스트 : deletedBoard.isDeleted()==false"); // ture 여야함

        // 게시글에 댓글이 없다면 그대로 테스트 끝.
        if(deletedBoard.getReplys() == null || deletedBoard.getReplys().isEmpty() ){
            log.info("삭제된 게시글엔 댓글이 없음.");
            return;
        }
        
        // 게시글에 댓글이 달려있다면, 해당 댓글 또한 모두 삭제된 상태여야 함.
        log.info("삭제된 게시글에 댓글 있음.");
        deletedBoard.getReplys().forEach(reply -> {
            log.info("댓글 확인: "+reply.getReplyId()+" , isDeleted: "+reply.isDeleted());
            assertTrue(reply.isDeleted(), "testDelete 테스트 : reply.isDeleted()==false");
        });
    }

    @Test
    public void testGetList(){
        // 모든 게시물 가져온다(페이징X)
        service.getList().forEach(i->{
            log.info(i.toString());
        });
    }

    // @Test
    // public void testGetList2(){
    //     // 모든 게시물 가져온다(페이징O)

    //     // 컨트롤러 단
    //     int page = 2;// 현 페이지 (idx  단위)
    //     int amount = 10;// 한 페이지에 보여줄 게시물 수
    //     Sort sort = Sort.by(Direction.DESC,"boardId");
    //     Pageable pageable = PageRequest.of(page, amount, sort);

    //     // 서비스 단
    //     // Page<BoardDTO> boardDtoPage = service.getList(pageable);

    //     // repository 단
    //     log.info("====================================");
    //     Page<Board> boardPage = repository.getBoardListWithReply(pageable);

    //     // log.info("boardDtoPage: "+boardDtoPage);

    // }
    
}
