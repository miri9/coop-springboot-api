package com.study.springbootjpa.miri.service;

import java.util.List;
import java.util.stream.IntStream;

import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.repository.ReplyRepository;

public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository repository;
    public ReplyServiceImpl(ReplyRepository repository){
        this.repository = repository;
    }

    @Override
    public Reply insert(ReplyDTO reply) {
        // 저장한 entity 를 다시 반환
        Reply replyAfterInsert = repository.save(convertToEntity(reply));

        return replyAfterInsert;
    }
    @Override
    public ReplyDTO read(Long id) {
        // 1. 게시판 정보 가져오기
        // 2. 게시판 정보 기반으로 댓글 가져오기 (Board.replys)
        // Reply reply = repository.
        return null;
    }

    @Override
    public List<ReplyDTO> getList() {
        return null;
    }


    @Override
    public Reply update(ReplyDTO reply) {
        return null;
    }

    @Override
    public Reply delete(Long id) {
        return null;
    }
    
}
