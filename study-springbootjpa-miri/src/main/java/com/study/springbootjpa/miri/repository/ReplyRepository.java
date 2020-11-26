package com.study.springbootjpa.miri.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.domain.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * - getReply : Reply 의 id 를 인자로, Reply 단일 도메인 객체를 가져온다.
 * - getReplyList : Board 의 id를 인자로, Reply 도메인 객체들을 가져온다.
 * 
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Query(value = "select r from Reply r where r.replyId = :replyId")
    public Reply getReply(@Param("replyId") Long replyId);

    @Query(value = "select r from Reply r inner join Board b on b.boardId = :boardId order by  r.replyId")
    public List<Reply> getReplyList(@Param("boardId") Long boardId);
    
}
