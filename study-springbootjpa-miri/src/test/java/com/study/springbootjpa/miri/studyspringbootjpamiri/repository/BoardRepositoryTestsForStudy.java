// package com.study.springbootjpa.miri.studyspringbootjpamiri.repository;

// import com.study.springbootjpa.miri.StudySpringbootjpaMiriApplication;
// import com.study.springbootjpa.miri.domain.forTest.BoardEntity;
// import com.study.springbootjpa.miri.domain.forTest.BoardEntityWithReply;
// import com.study.springbootjpa.miri.repository.forTest.SampleBoardRepository;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import lombok.extern.log4j.Log4j2;

// // @Disabled
// @Log4j2
// @SpringBootTest(classes = StudySpringbootjpaMiriApplication.class) // 이거 없으면 bean 을 못찾음. 찾아볼것.
// public class BoardRepositoryTestsForStudy {

//     @Autowired
//     private SampleBoardRepository repository;

//     // [테스트용] 단일 게시글 가져오기
//     @Test
//     public void getBoard(){
//         BoardEntity board = repository.getBoard(8L);
//         BoardEntityWithReply boardWithoutReply = repository.getBoard(8L);

//         log.info("===result===");
//         log.info(board.toString());
//         /**
//          * [쿼리]
//          * Hibernate: 
//             select
//                 board0_.board_id as board_id1_0_,
//                 board0_.content as content2_0_,
//                 board0_.created_at as created_at3_0_,
//                 board0_.is_deleted as is_deleted4_0_,
//                 board0_.modified_at as modified_at5_0_,
//                 board0_.password as password6_0_,
//                 board0_.title as title7_0_,
//                 board0_.writer as writer8_0_ 
//             from
//                 tbl_board board0_
//             [출력] : repyls 없음. 실제 8번은 
//             Board(boardId=8, 
//             title=8번 제목입니다.,
//             content=<p>8번 내용입니다.</p>, 
//             writer=writer3, password=pw3, 
//             createdAt=2020-11-26T02:21:06,
//             modifiedAt=2020-11-26T02:21:06,
//             isDeleted=true)
//          */
//     } 
    
// }
