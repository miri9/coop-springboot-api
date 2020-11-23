package com.study.springbootjpa.miri.controller;

import java.util.List;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.service.BoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** ResponseEntity
 * - 리플렉션과 프록싱
 * - 객체를 "감싸서" 프록싱/리플렉션이 걸리지 않게끔 하는 용도. (인지하지 못하는 데이터 변이를 막기 위함.)
 * - 예) 컬렉션 객체 => 순수 객체가 아닌 객체 집합 => "List<Board> 여도 직접 까봐야 실제 타입을 앎"
 *   브레이크 포인트 찍으면, new ArrayList<>() 여부에 따라 이상한 타입으로 캐스팅 될 수 O
 *   결론 = java 가 제대로 인식할 수 있게+데이터 변이 막기 위해 순수 객체로 감싸는 것
 */
/** BoardRestController 
 * 
 * by miri
 * 
 * - readBoard : board 와 그에 포함된 댓글(선택) 가져온다.
 * - getBoardList :
 *   특정 페이지에 해당하는 board 의 리스트 가져온다.
 *   (페이징X 경우 : 현재 이것만 구현) 모든 데이터를 뿌려준다.
 *  
     // TODO : 현재 페이지 정보(현재 페이지, n개씩 보기) 를 받아 List<Board> 를 가져오게끔 수정
 *   (페이징O 경우) CurrentPage 와 Pagination 을 이용해 페이징된 데이터를 보내준다.
 *  
 * - insertBoard : 화면으로부터 dto 를 받아, 유효성 검사 후 DB 에 삽입
 * - deleteBoard : 화면으로부터 id를 받아, 해당 board 를 DB 에서 제거 
 * - updateBoard : 화면으로부터 dto 를 받아, 해당 DB 로 업데이트
 * 
 * 
 */
// 규칙 : CRUD 메서드는 무조건 해당 작업의 대상인 domain 객체를 반환하며(혹은 적어도 키값), 성공/실패 로그를 출력한다.
@RestController
@RequestMapping(value = "board")
public class BoardRestController{

    private final BoardService boardService;
    public BoardRestController(BoardService service){
        // 생성자 주입
        this.boardService = service;
    }

    // 1. get board (하나의 board 조회)
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BoardDTO> readBoard(@PathVariable("id") Long id,Model model){
        /**
         * 해당 id 에 해당하는 board 가져오기
         */
        BoardDTO result = boardService.read(id);

        // 날 객체로 감싸는 한이 있더라도 내 제어권 안에 있는 객체로 제어하기 위함.
        // 감싸서 보내기 : 바깥 위상으로의 통신을 위함
        // entity 하나로 처음부터 끝까지 게시판 만들기 ? => NO : entity 캐싱 영역에서도 내부 프락싱이 일어나는데 ㄴㄴ
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    // 2. get boardList (boardList 조회, 페이징 및 조인 필요)
    @GetMapping(value = "/list")
    public ResponseEntity<List<BoardDTO>> getBoardList(Model model){
        /**
         * 기본 한 페이지당 5개의 게시물을 기반으로, 데이터를 받아 Set<Board> 를 가져온다.
         * 해당 board 에 달린 reply 의 총 count 를 받아온다.
         */
        List<BoardDTO> result = boardService.getList();

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    // insert board
    @PostMapping(value = "/write")
    public ResponseEntity<BoardDTO> insertBoard(BoardDTO board){
        /**
         * 화면으로부터 dto 를 받아, 유효성 검사 후 DB 에 삽입
         */
        Board boardAfterInsert = boardService.convertToEntity(boardService.insert(board));



        // error msg : Cannot infer type arguments for ResponseEntity<>
        return new ResponseEntity<>(boardAfterInsert,HttpStatus.OK);
    }
    
    // delete board
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BoardDTO> deleteBoard(@PathVariable("id") Long id){
        /**
         * 화면으로부터 id 를 받아, 해당하는 게시글을 DB 에서 삭제
         */
        Board boardAfterDelete = boardService.delete(board.getBoard_id);

        // error msg : Cannot infer type arguments for ResponseEntity<>
        return new ResponseEntity<>(boardAfterDelete,HttpStatus.OK);
    }
    // update board (put)
    // put OR patch method 차이점 : https://papababo.tistory.com/269 참고. 
    @DeleteMapping(value = "/update/{id}")
    public ResponseEntity<BoardDTO> updateBoard(BoardDTO board){
        /**
         * 화면으로부터 dto 를 받아, 해당하는 게시글을 update
         */
        BoardDTO boardAfterUpdate = boardService.update(board);

        return new ResponseEntity<>(boardAfterUpdate,HttpStatus.OK);
    }

}
