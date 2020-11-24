package com.study.springbootjpa.miri.repository;

import java.util.List;

import com.study.springbootjpa.miri.domain.Board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * - getBoardWithReply : reply 정보가 포함된 단일 board 도메인 객체를 가져온다.
 * - getBoardListWithReply(인자 없음 => 페이징 X) : reply 정보가 포함된 board 도메인 객체들을 가져온다.
 * - delete : board id 를 인자로, 해당 객체를 삭제한다. 마지막에 삭제된 객체 정보를 반환한다.
 * 
 */
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    // @EntityGraph(attributePaths = "replys")
    @Query(value = "select b from Board b where b.board_id = :id")
    public Board getBoardWithReply(@Param("id") Long id);

    // @EntityGraph(attributePaths = "replys")
    @Query(value = "select b from Board b")
    public List<Board> getBoardListWithReply();

    @Query(value = "delete from Board b where b.board_id = :id")
    public Board delete(@Param("id") Long id);
}
