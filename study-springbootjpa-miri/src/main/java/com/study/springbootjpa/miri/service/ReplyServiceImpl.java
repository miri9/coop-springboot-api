package com.study.springbootjpa.miri.service;

import java.util.List;
import java.util.stream.Collectors;


import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.repository.ReplyRepository;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository repository;
    public ReplyServiceImpl(ReplyRepository repository){
        this.repository = repository;
    }

    @Override
    public ReplyDTO insert(ReplyDTO reply) {
        Reply replyAfterInsert = repository.save(convertToEntity(reply));

        return convertToDto(replyAfterInsert);
    }

    @Override
    public ReplyDTO read(Long reply_id) {
        Reply reply = repository.getReply(reply_id);
        return convertToDto(reply);
    }

    @Override
    public List<ReplyDTO> getList(Long board_id) {
        List<ReplyDTO> replys = repository.getReplyList(board_id)
                                .stream()
                                .map(i->convertToDto(i))
                                .collect(Collectors.toList());
        return replys;
    }


    @Override
    public ReplyDTO update(ReplyDTO reply) {
        // 새 reply 로 기존 reply 덮어씌우기
        Reply replyUpdated = repository.save(convertToEntity(reply));
        return convertToDto(replyUpdated);
    }

    @Override
    public boolean delete(Long reply_id) {
        // 기존 reply : 추후 비교 로직 필요한가?
        Reply replyToDelete = repository.findById(reply_id).get(); // 디버깅용
        // log.info("ReplyServiceImpl.delete - replyToDelete: "+replyToDelete);// 디버깅용

        // 실제로 삭제한 reply
        // Reply replyDeleted = repository.delete(reply_id);
        
        log.info("ReplyServiceImpl.delete - replyToDelete: "+replyToDelete);
        boolean hasSuccessedQuery = repository.delete(reply_id)>0? true:false; // hasSuccessedQuery: @Modifying@Query 삭제 성공 횟수


        return hasSuccessedQuery;
    }
    
}
