package com.study.springbootjpa.miri.controller;

import java.util.List;

import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.service.BoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
 * [규칙]
 * 1. CRUD 메서드는 무조건 해당 작업의 대상인 domain 객체를 반환
 * 2. 성공/실패 로그를 출력 'AOP'
 */
@RestController
@RequestMapping(value = "board")
@CrossOrigin("*")
public class BoardRestController{
    private final BoardService boardService;
    public BoardRestController(BoardService service){
        this.boardService = service;
    }

    /**
     * 해당 id 에 해당하는 board 가져오기
     * @param id
     * @return
     */
    @ApiOperation(value = "[SELECT] 인자 boardId 를 받아 Board 가져오기")
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BoardDTO> readBoard(
        @ApiParam(value = "DB에 등록된 board 의 Id.")
        @PathVariable("id") Long id){
        BoardDTO result = boardService.read(id);

        
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * 현재 : 페이징 없이 모든 게시물을 가져온다.
     * @return
     */
    @ApiOperation(value = "[SELECT] 페이징 없이 모든 Board 와 그에 딸린 Reply 가져오기")
    @GetMapping(value = "/list")
    public ResponseEntity<List<BoardDTO>> getBoardList(){
        List<BoardDTO> result = boardService.getList();

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    /**
     * 화면으로부터 dto 를 받아 DB 에 인서트
     * @param board
     * @return
     */
    @ApiOperation(value = "[INSERT] 인자로 BoardDTO 를 받아 DB에 등록")
    @PostMapping(value = "/write")
    public ResponseEntity<BoardDTO> insertBoard(
        @RequestParam(value = "boardId") 
        @ApiParam(value = "boardId 를 제외하고 모든 필드에 값을 채운 BoardDTO.")
        @RequestBody 
        @Validated 
        BoardDTO board){
            BoardDTO boardAfterInsert = boardService.insert(board);

            return new ResponseEntity<>(boardAfterInsert,HttpStatus.OK);
    }
    
    /**
     * 화면으로부터 id 를 받아, 해당하는 게시글을 DB 에서 삭제 (isDeleted = true)
     * @param id
     * @return
     */
    @ApiOperation(value = "[DELTE] 인자로 boardId 를 받아 삭제(Board.isDelete = true)")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BoardDTO> deleteBoard(
        @ApiParam(value = "DB에 등록된 board 의 Id.")
        @PathVariable("id") Long id) {
        BoardDTO deletedBoard = boardService.delete(id);

        return new ResponseEntity<>(deletedBoard,HttpStatus.OK);
    }

    /**
     * 화면으로부터 dto 를 받아, 해당하는 게시글을 update
     * put OR patch method 차이점 : https://papababo.tistory.com/269 참고. 
     * @param board
     * @return
     */
    @ApiOperation(value = "[UPDATE] 인자로 BoardDTO 를 받아 수정")
    @PutMapping(value = "/update")
    public ResponseEntity<BoardDTO> updateBoard(
        @RequestParam(value = "BoardDTO")
        @ApiParam(value = "필드에 변경 사항이 생긴 BoardDTO.")
        @RequestBody @Validated BoardDTO board){
        BoardDTO boardAfterUpdate = boardService.update(board);

        return new ResponseEntity<>(boardAfterUpdate,HttpStatus.OK);
    }

}
