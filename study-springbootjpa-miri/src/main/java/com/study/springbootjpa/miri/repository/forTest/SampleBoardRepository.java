// package com.study.springbootjpa.miri.repository.forTest;

// import java.util.List;

// import com.study.springbootjpa.miri.domain.Board;
// import com.study.springbootjpa.miri.domain.forTest.BoardEntity;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// /**
//  * @Query 공부용 리포지토리
//  */
// @Repository
// public interface SampleBoardRepository  extends JpaRepository<BoardEntity,Long> {
//     // [테스트용] 단일 게시글 가져오기
//     @Query(value = "select b from Board b where b.boardId = :id")
//     public BoardEntity getBoard(@Param("id") Long id);

//     @Query(value = "select b from Board b where b.boardId = :id")
//     public BoardEntity getBoard(@Param("id") Long id);
    
//     // 게시판 List : 게시글만 가져오기
//     // 게시판 List : 게시글 과 댓글 수 가져오기

//     // 게시글 read : 게시글 가져오기
//     // 게시판 List : 게시글과 댓글 리스트 모두 가져오기



//     /**
//      * 
//      *  @Query(value = "select b from Board b where b.boardId = :id")
//     public Board getBoardWithReply(@Param("id") Long id);

//     // 여러 개 게시글 가져오기 + 페이징 o
//     @Query(value = "select b,b.replys from Board b",
//     countQuery = "select count(b) from Board b")
//     public Page<Board> getBoardListWithReply(Pageable pageable);

//     // 여러 개 게시글 가져오기 + 페이징 X
//     // @Query(value = "select b,b.replys from Board b")
//     // public List<Board> getBoardListWithReply();
//     @Query(value = "select b from Board b")
//     public List<Board> getBoardListWithReply();
//      */
//      // 단일 게시글 가져오기 + 페이징 x
    
// }
