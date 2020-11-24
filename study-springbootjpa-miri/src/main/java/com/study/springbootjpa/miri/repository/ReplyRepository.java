package com.study.springbootjpa.miri.repository;

import java.util.List;

import com.study.springbootjpa.miri.domain.Reply;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * - getReplyWithBoard : Board 정보가 포함된 Reply 단일 도메인 객체를 가져온다.
 * - getReplyListWithBoard : Board 정보가 포함된 Reply 도메인 객체들을 가져온다.
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @EntityGraph(attributePaths = "board")
    @Query(value = "select r from Reply r where r.reply_id = :id")
    public List<Reply> getReplyWithBoard(@Param("id") Long id);
    
}
