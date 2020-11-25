package com.study.springbootjpa.miri.studyspringbootjpamiri.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.service.BoardService;
import com.study.springbootjpa.miri.service.ReplyService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

/**
 * 유효성 검사 제외하고 가장 기본적인 CRUD 를 확인하는 케이스모음.
 * 
 * - testGet : 특정 게시물에 속한 댓글 Reply 하나 가져오기 - testGetList : 특정 게시물에 속한 댓글 Reply
 * 모두 가져오기
 * - testGetList
 * 
 *
 * - testInsert
 * - testUpdate
 * - testDelete
 */
@Disabled
@Log4j2
@SpringBootTest(classes = StudySpringbootjpaMiriApplication.class)
public class ReplyServiceTests {
    @Autowired
    private ReplyService service;

    @Autowired
    private BoardService boardService;

    @Test
    public void testInsert(){
        Long boardId = 5L;
        // board(n번) 에 단일 Reply DTO 만들고, repository 로 save
        ReplyDTO replyBeforeInsert = ReplyDTO.builder()
                                    .replyer("replyer")
                                    .replyPassword("rpely_password")
                                    .replyContent("reply_content")
                                    .createdAt(LocalDateTime.now())
                                    .isDeleted(false)
                                    .board(boardId)
                                    .build();

        log.info("replyBeforeInsert: "+replyBeforeInsert.toString());
        
        
        ReplyDTO replyAfterInsert = service.insert(replyBeforeInsert);

        log.info("replyAfterInsert: "+replyAfterInsert.toString());

        // board 객체 가져와 reply 수 확인하기
        boardService.read(boardId).getReplys().forEach(reply -> {
            log.info("Board.replys 의 각 reply: "+reply.toString());
        });
    }
    // @Transactional
    @Test
    public void testInsert2(){
        // 게시물 5번부터 10번까지 , 각각 댓글 5개를 단다.
        Long boardStart = 5L; // id
        Long boardEnd = 10L; // id
        int replyStart = 1; // 개수
        int replyEnd = 5; // 개수

        // 댓글 5개를 만들어 repository 에 save
        for(long i=boardStart; i<=boardEnd; i++){
            log.info("====================== i:"+ i);
            for(int j=replyStart; j<=replyEnd; j++){
                log.info("====================== j:"+ j);
                ReplyDTO replyBeforeInsert = ReplyDTO.builder()
                .replyer("replyer"+j)
                .replyPassword("rpely_password" + j)
                .replyContent("reply_content"+j)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .board(i)
                .build();

                log.info("replyBeforeInsert: "+replyBeforeInsert);
                
                // 2-2. replyRepository 에 replyBeforeInsert save.
                // log.info("=========save "+j+"=========");
                ReplyDTO replyAfterInsert = service.insert(replyBeforeInsert);
            }
        }

        // 게시물을 불러와 toString 하여 replys 필드 출력하기
        for(long i=boardStart; i<=boardEnd; i++){
            log.info("[게시물] "+i+"번 게시물 replys 출력중.....");
            boardService.read(i).getReplys().forEach(reply->{
                log.info("[단일 댓글] "+reply.getReplyId()+":"+reply.getReplyer()+":"+reply.getReplyContent());
            });
        }
    }
    @Test
    public void testUpdate(){
        // target번 댓글의 content 를 한글로 변경한다.
        Long target = 85L;

        ReplyDTO reply = service.read(target); // 기존 댓글
        String newContent = target+"번 댓글입니다!";
        reply.setReplyContent(newContent);

        // repository save
        ReplyDTO updatedReply = service.update(reply);

        // 새로 업데이트한 댓글 필드가 의도에 맞게 변경됬는지 확인
        assertTrue( (updatedReply.getReplyContent()).equals(newContent), "testUpdate 테스트 : 변경 전/후 내용 불일치");
        assertTrue(target == updatedReply.getReplyId(), "testUpdate 테스트 : 아이디 불일치");
        
    }
    @Test
    public void testDelete(){
        // target번 댓글의 isDeleted = true 로 변경한다.
        Long target = 86L;

        // 삭제 전 DTO 받아오기
        ReplyDTO originalReply  = service.read(target);
        log.info("originalReply: "+originalReply.toString());

        // 삭제 후, 해당 DTO 를 받아온다.
        ReplyDTO deletedReply  = service.delete(target);

        // DTO 출력
        log.info("deletedReply: "+deletedReply.toString());

        assertTrue(target == deletedReply.getReplyId() && deletedReply.isDeleted() == true, "testDelete 테스트 : id 혹은 isDeleted 문제");

    }

}
