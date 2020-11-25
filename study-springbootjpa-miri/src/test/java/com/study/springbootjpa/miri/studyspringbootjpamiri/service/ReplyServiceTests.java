// package com.study.springbootjpa.miri.studyspringbootjpamiri.service;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.IntStream;
// import java.util.stream.LongStream;

// import javax.transaction.Transactional;

// import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
// import com.study.springbootjpa.miri.dto.BoardDTO;
// import com.study.springbootjpa.miri.dto.ReplyDTO;
// import com.study.springbootjpa.miri.service.BoardService;
// import com.study.springbootjpa.miri.service.ReplyService;

// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import lombok.extern.log4j.Log4j2;

// /**
//  * 유효성 검사 제외하고 가장 기본적인 CRUD 를 확인하는 케이스모음.
//  * 
//  * - testGet : 특정 게시물에 속한 댓글 Reply 하나 가져오기 - testGetList : 특정 게시물에 속한 댓글 Reply
//  * 모두 가져오기
//  * - testGetList
//  * 
//  *
//  * - testInsert
//  * - testUpdate
//  * - testDelete
//  */
// @Disabled
// @Log4j2
// @SpringBootTest(classes = StudySpringbootjpaMiriApplication.class)
// public class ReplyServiceTests {
   
//     @Autowired
//     private ReplyService service;

//     @Autowired
//     private BoardService boardService;

//     // @Rollback
//     @Transactional
//     @Test
//     public void testInsert(){
//         // 게시물 5번부터 10번까지 , 각각 댓글 5개를 단다.
//         Long boardStart = 5L;
//         Long boardEnd = 10L;
//         int replyStart = 1;
//         int replyEnd = 5;
        
        
//         // 게시물을 가져온다. (boardTarget)
//         // 게시물에 댓글을 5개씩 저장한다. (boardTarget.replyTarget에 저장)
//         List<BoardDTO> boardList =new ArrayList<>();
//         LongStream.rangeClosed(boardStart, boardEnd).forEach(i->{ // 게시물 for문

//             BoardDTO boardTarget = boardService.read(i);
//             List<ReplyDTO> replyTarget = boardTarget.getReplys();

//             // 댓글을 다섯개씩 만든다
//             IntStream.rangeClosed(replyStart, replyEnd).forEach(j->{ // 댓글 for문
//                 ReplyDTO newReply = ReplyDTO.builder()
//                 .replyer("replyer"+j)
//                 .rpely_password("rpely_password"+j)
//                 .reply_content("reply_content"+j)
//                 .createdAt(LocalDateTime.now())
//                 .build();

//                 replyTarget.add(newReply);
//             });

//             // boardList 에 "댓글을 포함한 게시물" 을 저장.
//             boardTarget.setReplys(replyTarget);
//             boardList.add(boardTarget);
//         });

//         // 생각대로 각 게시물에 각 댓글이 정확히 만들어졌는지 확인한다.
//         boardList.forEach(board -> {
//             log.info("======board 시작=======");
//             log.info(board.toString());
//             log.info("==board.replys 시작==");
//             board.getReplys().forEach(reply -> reply.toString());
//             log.info("==board.replys  끝==");
//             log.info("======board  끝=======");
//         });

//         // board 를 save한다.
//         boardService.save(board)
//     }

// }
