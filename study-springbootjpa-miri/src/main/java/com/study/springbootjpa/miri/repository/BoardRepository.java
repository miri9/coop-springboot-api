package com.study.springbootjpa.miri.repository;

import com.study.springbootjpa.miri.domain.Board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * - getBoardWithReply : reply 정보가 포함된 단일 board 도메인 객체를 가져온다.
 * - getBoardListWithReply : reply 정보가 포함된 board 도메인 객체들을 가져온다.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    @EntityGraph(attributePaths = "replys")
    @Query(value = "select b from Board b where b.board_id = :id")
    public Board getBoardWithReply(@Param("id") Long id);
}
