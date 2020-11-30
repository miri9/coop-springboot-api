package com.study.springbootjpa.miri.controller;

import java.util.List;

import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.service.BoardService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
 * 2. (미완성) 성공/실패 로그를 출력 'AOP'
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
     * @return ResponseEntity<BoardDTO>
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
     * @return ResponseEntity<Page<BoardDTO>>
     * 
     */
    @ApiOperation(value = "[SELECT] 페이징 없이 모든 Board 가져오기")
    @GetMapping(value = "/list")
    public ResponseEntity<List<BoardDTO>> getBoardList(){
        List<BoardDTO> result = boardService.getList();

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * 페이징과 함께 모든 게시물을 가져온다.
     * 
     * [프론트에서 보낼 인자]
     * 1. page : 현재 페이지 (idx 이므로 0부터 시작.즉 1페이지는 page = 0.)
     * 2. amount : 한 페이지에 보여줄 게시글 수 
     * 
     * [백엔드에서 준비할 인자]
     * 1. Sort sort : order by + JPA
     * 2. Pageable pageable : page,size,sort 기반
     * => Page<BoardDTO> 반환
     * 
     * @return ResponseEntity<Page<BoardDTO>>
     */
    @ApiOperation(value = "[SELECT] 모든 Board 와 그에 딸린 Reply 가져오기 => 총 게시글 수 포함하여 Page 객체로 감싸 보냄")
    @GetMapping(value = "/list-paging")
    public ResponseEntity<Page<BoardDTO>> getBoardListWithPaging(
        @ApiParam(value = "게시판에서 현재 페이지 (idx 기반이므로 첫 페이지는 page=0 부터 시작)")
        @RequestParam(defaultValue = "0") int page,
        @ApiParam(value = "한 페이지 당 몇 개의 게시글을 보여줄 지 설정")
        @RequestParam(defaultValue = "5") int amount)
    {
        // 프론트에서 보내는 인자
        // int page = 0;
        // int amount = 10;

        // 백엔드에서 준비하는 인자
        Sort sort = Sort.by(Direction.DESC, "boardId"); // board 내림차순
        Pageable pageable = PageRequest.of(page, amount, sort);

        Page<BoardDTO> result = boardService.getList(pageable);


        

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    /**
     * 화면으로부터 dto 를 받아 DB 에 인서트
     * @param board
     * @return ResponseEntity<BoardDTO>
     */
    @ApiOperation(value = "[INSERT] 인자로 BoardDTO 를 받아 DB에 등록")
    @PostMapping(value = "/write")
    public ResponseEntity<BoardDTO> insertBoard(
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
     * @return ResponseEntity<BoardDTO>
     */
    @ApiOperation(value = "[DELTE] 인자로 boardId 를 받아 삭제(Board.isDelete, Board.replys 에 속한 Reply.isDelete = true)")
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
     * @return ResponseEntity<BoardDTO>
     */
    @ApiOperation(value = "[UPDATE] 인자로 BoardDTO 를 받아 수정")
    @PutMapping(value = "/update")
    public ResponseEntity<BoardDTO> updateBoard(
        // @RequestParam(value = "BoardDTO",required = true)
        @ApiParam(value = "boardId 를 포함하며, 필드에 변경 사항이 생긴 BoardDTO.")
        @RequestBody @Validated BoardDTO board){
        BoardDTO boardAfterUpdate = boardService.update(board);

        return new ResponseEntity<>(boardAfterUpdate,HttpStatus.OK);
    }

}
