package com.study.springbootjpa.miri.repository;

import java.util.List;

import com.study.springbootjpa.miri.domain.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * - getBoardWithReply : reply 정보가 포함된 단일 board 도메인 객체를 가져온다.
 * - getBoardListWithReply(인자 없음 => 페이징 X) : reply 정보가 포함된 board 도메인 객체들을 가져온다.
 * - getBoardListWithReply(Pageable  => 페이징 O) : reply 정보가 포함된 board 도메인 객체들을 가져온다.
 * 
 */
@FunctionalInterface
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    /**
     * @Query.nativeQuery 속성 : boolean
        Configures whether the given query is a native one. Defaults to false.
        Default: false
     */
    // 단일 게시글 가져오기 + 페이징 x
    @Query(value = "select b from Board b where b.boardId = :id")
    public Board getBoardWithReply(@Param("id") Long id);

    // 여러 개 게시글 가져오기 + 페이징 o
    // @Query(value = "select b,b.replys from Board b order by b.boardId",
    @Query(value = "select b from Board b",
    countQuery = "select count(b) from Board b")
    public Page<Board> getBoardListWithReply(Pageable pageable);
    
    // 여러 개 게시글 가져오기 + 페이징 X
    // @Query(value = "select b,b.replys from Board b") // 네..
    // public List<Board> getBoardListWithReply();
    @Query(value = "select b from Board b")
    public List<Board> getBoardListWithReply();


}
