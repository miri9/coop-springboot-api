package com.study.springbootjpa.miri.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.domain.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
/**
 * - getReply : Reply 의 id 를 인자로, Reply 단일 도메인 객체를 가져온다.
 * - getReplyList : Board 의 id를 인자로, Reply 도메인 객체들을 가져온다.
 * - delete : Reply id 를 인자로, 해당 객체를 삭제한다. 삭제 후 삭제된 Reply 객체를 반환한다.
 * 
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Query(value = "select r from Reply r where r.reply_id = :reply_id")
    public Reply getReply(@Param("reply_id") Long reply_id);

    // TODO : reply 안에 board 값을 넣어야 할 필요가 있을까?
    @Query(value = "select r from Reply r inner join Board b on b.board_id = :board_id")
    public List<Reply> getReplyList(@Param("board_id") Long board_id);
    
    @Transactional
    @Modifying
    @Query(value = "delete from Reply r where r.reply_id = :reply_id")
    public int delete(@Param("reply_id") Long reply_id);
    
}
