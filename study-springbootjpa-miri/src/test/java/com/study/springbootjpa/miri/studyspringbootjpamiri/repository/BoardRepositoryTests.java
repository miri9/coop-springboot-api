package com.study.springbootjpa.miri.studyspringbootjpamiri.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
import com.study.springbootjpa.miri.domain.Board;
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

/**
 * 페이징 및 트랜잭션 이슈 있는 repository query 모아서 테스트.
 * - testGetListWithPaging
 */
// @Disabled
@Log4j2
@SpringBootTest(classes = StudySpringbootjpaMiriApplication.class) // 이거 없으면 bean 을 못찾음. 찾아볼것.
public class BoardRepositoryTests {
    @Autowired
    private BoardServiceImpl service;
    
    @Autowired
    private BoardRepository repository;


    // Page<Board> -> Page<BoardDTO> 변환 위함
    private final Function<Board,Board> boardPageConveter = new Function<Board,Board>(){
        @Override
        public Board apply(Board board) {
            return Board.builder().boardId(board.getBoardId()).build();
            // log.info("=============apply=============");
            // return service.convertToDto(board);
        }
    };
    
    @Test
    public void testGetListWithPaging(){
        /**
         * [이슈]
         * Page<Board> -> Page<BoardDTO> 로 변환할때,
         * Page<BoardDTO> boardDtoPages = boardPages.map(board -> boardPageConveter.apply(board));
         * ... 이렇게 map 을 쓰면 다음과 같은 예외 발생.
         * 
         * [Ljava.lang.Object; cannot be cast to com.study.springbootjpa.miri.domain.Board]
         * 
         * boardPages.getContent().forEach(board -> { ...}) 부분에서 해당 예외 발생함을 확인.
         * getContent() 로 반환되는 타입이 java.util.Collections$UnmodifiableRandomAccessList.
         * 
         * 관련 키워드
         * - subList 와 직렬화 (https://sungminhong.github.io/java/serialize/)
         * - Class Collections.UnmodifiableCollection<E> (https://runtimeverification.com/monitor/propertydb/index.html?java/util/Collections.UnmodifiableRandomAccessList.html)
         * - Class Collections 의 unmodifiableCollection 메서드 (https://runtimeverification.com/monitor/propertydb/index.html?java/util/Collections.UnmodifiableRandomAccessList.html)
         *   => Returns an unmodifiable view of the specified collection.
         *      This method allows modules to provide users with "read-only" access to internal collections.
         *      Query operations on the returned collection "read through" to the specified collection,
         *      and attempts to modify the returned collection, whether direct or via its iterator,
         *      result in an UnsupportedOperationException.
         * - https://www.freeism.co.kr/wp/archives/1608
         */
        // 임시 Pageable
        int page = 2;// 현 페이지 (idx  단위)
        int amount = 10;// 한 페이지에 보여줄 게시물 수
        Sort sort = Sort.by(Direction.DESC,"boardId");
        Pageable pageable = PageRequest.of(page, amount, sort);

        // repository 메서드 호출
        log.info("=============getBoardListWithReply 호출=============");
        Page<Board> boardPages = repository.getBoardListWithReply(pageable);


        // 그렇다면 Page<Board>.getContent() => List<Board> board 의 클래스명?
        // java.util.Collections$UnmodifiableRandomAccessList
        //List<Board> board = boardPages.getContent();
        // log.info(board.getClass().getName());

        // forEach 로 따로 저장해야하나?
        // arrayList 로 변환하면?
        // 출력 : java.util.ArrayList
        ArrayList<Board> board2 = new ArrayList<>();
        board2.addAll(boardPages.getContent());
        log.info(board2.getClass().getName());
        log.info(board2.toString());

        

        // 그럼 ArrayList 로 감쌀까?
        // 출력 : java.util.ArrayList
        // List<Board> board = new ArrayList<Board>(boardPages.getContent());
        // log.info(board.getClass().getName());



        // // Page<Board> -> Page<BoardDTO> 로 변환
        // log.info("=============boardDtoPages 값 할당=============");
        // Page<Board> boardPages2 = boardPages.map(b->boardPageConveter.apply(b));
    }
}
