package com.study.springbootjpa.miri.controller;

import java.util.List;

import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.service.ReplyService;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * ReplyRestController
 * 
 * by miri
 * 
 * - getReplyList : 특정 board 에 포함된 모든 reply 리스트 가져온다.
 * - insertReply : 특정 board 에 속한 reply를 생성해 DB에 저장한다.
 * - updateReply : 특정 id 를 가진 reply 의 필드를 수정한다.
 * - deleteReply : 특정 id 를 가진 reply 를 삭제한다. (reply.isDeleted = true)
 * 
 * [규칙]
 * 1. CRUD 메서드는 무조건 해당 작업의 대상인 domain 객체를 반환
 * 2. 성공/실패 로그를 출력 'AOP'
 */
@RestController
@RequestMapping(value = "reply")
@CrossOrigin("*")
public class ReplyRestController{
    private final ReplyService replyService;
    public ReplyRestController(ReplyService service){
        this.replyService = service;
    }

    /**
     * 특정 board 에 포함된 모든 reply 리스트 가져온다.
     * 다만 게시글 화면에서 board 에 포함된 board.replys 를 가져오므로 쓸 이유는 크게 없을 듯.
     * @param boardId
     * @return ResponseEntity<List<ReplyDTO>>
     */
    @ApiOperation(value = "[SELECT] (페이징 X) 인자 boardId 를 받아 해당 Board 의 reply 리스트만 모두 가져오기")
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(
        @ApiParam(value = "reply들이 속한 board 의 Id.")
        @PathVariable("boardId") Long boardId)
    {

        List<ReplyDTO> result = replyService.getList(boardId);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    /**
     * 특정 board 에 속한 reply를 생성해 DB에 저장한다.
     * @param reply
     * @return ResponseEntity<ReplyDTO>
     */
    @ApiOperation(value = "[INSERT] 인자로 ReplyDTO 를 받아 DB에 등록")
    @PostMapping("/write")
    public ResponseEntity<ReplyDTO> insertReply(
        @ApiParam(value = "replyId 를 제외하고 모든 필드에 값을 채운 ReplyDTO.")
        @RequestBody
        @Validated ReplyDTO reply)
    {
        ReplyDTO replyAfterInsert = replyService.insert(reply);

        return new ResponseEntity<>(replyAfterInsert,HttpStatus.OK);
    }

    /**
     * 특정 id 를 가진 reply 의 필드를 수정한다.
     * @param reply
     * @return ResponseEntity<ReplyDTO>
     */
    @ApiOperation(value = "[UPDATE] 인자로 ReplyDTO 를 받아 수정")
    @PutMapping(value = "/update")
    public ResponseEntity<ReplyDTO> updateReply(
        @ApiParam(value = "replyId 를 포함하며, 모든 필드에 값을 채운 ReplyDTO.")
        @RequestBody
        @Validated
        ReplyDTO reply)
    {
        ReplyDTO replyAfterUpdate = replyService.update(reply);

        return new ResponseEntity<>(replyAfterUpdate,HttpStatus.OK);
    }

    /**
     * 특정 id 를 가진 reply 를 삭제한다. (reply.isDeleted = true)
     * @param id
     * @return ResponseEntity<ReplyDTO>
     */
    @ApiOperation(value = "[DELTE] 인자로 replyId 를 받아 삭제(Reply.isDelete = true)")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ReplyDTO> deleteReply(
        @ApiParam(value = "DB에 등록된 reply 의 Id.")
        @PathVariable("id") Long id)
    {
        ReplyDTO replyAfterDelete = replyService.delete(id);
        return new ResponseEntity<>(replyAfterDelete,HttpStatus.OK);
    }

}
