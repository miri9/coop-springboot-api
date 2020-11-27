package com.study.springbootjpa.miri.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.repository.ReplyRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository repository;
    private final Function<Reply,ReplyDTO> replyPageConveter; // Page<Reply> -> Page<ReplyDTO> 변환 위함

    public ReplyServiceImpl(ReplyRepository repository) {
        this.repository = repository;
        this.replyPageConveter = new Function<Reply,ReplyDTO>(){
			@Override
			public ReplyDTO apply(Reply reply) {
				return convertToDto(reply);
            }
        };
    }

    @Override
    // public ReplyDTO insert(ReplyDTO reply, Long boardId) {
    public ReplyDTO insert(ReplyDTO reply) {
        // reply.setBoard(boardId);

        Reply replyBeforeInsert = convertToEntity(reply);
        log.info("ReplyServiceImpl.insert - replyBeforeInsert: " + replyBeforeInsert);

        Reply replyAfterInsert = repository.save(replyBeforeInsert);
        log.info("ReplyServiceImpl.insert - replyAfterInsert: " + replyAfterInsert);
        
        return convertToDto(replyAfterInsert);
    }
    
    @Override
    public ReplyDTO read(Long replyId) {
        Reply reply = repository.getReply(replyId);
        return convertToDto(reply);
    }
    
    // 페이징 x
    @Override
    public List<ReplyDTO> getList(Long boardId) {
        List<ReplyDTO> replys = repository.getReplyList(boardId).stream().map(i -> convertToDto(i))
        .collect(Collectors.toList());
        return replys;
    }

    // 페이징 o
    @Override
    public Page<ReplyDTO> getList(Pageable pageable, Long boardId) {
        log.info("ReplyServiceImpl getList - param pageable.pageSize: "+pageable.getPageSize());
        log.info("ReplyServiceImpl getList - param pageable.getPageNumber: "+pageable.getPageNumber());
        
        Page<Reply> replyPages = repository.getReplyList(pageable,boardId);

        // Page<Reply> -> Page<ReplyDTO> 로 변환
        Page<ReplyDTO> replyDtoPage = replyPages.map(reply -> replyPageConveter.apply(reply));
        
        return replyDtoPage;
    }

    @Override
    public ReplyDTO update(ReplyDTO reply) {
        // 새 reply 로 기존 reply 덮어씌우기
        log.info("ReplyServiceImpl.update - replyBeforeUpdate: " + reply.toString());

        Reply replyUpdated = repository.save(convertToEntity(reply));

        log.info("ReplyServiceImpl.update - replyUpdated: " + replyUpdated.toString());

        return convertToDto(replyUpdated);
    }

    @Override
    public ReplyDTO delete(Long replyId) {
        // log.info("ReplyServiceImpl.delete - replyToDelete: "+replyToDelete);

        // 1. 삭제할 댓글 가져오기
        ReplyDTO replyToDelete = convertToDto(repository.getReply(replyId));

        // 2. 댓글 삭제 ( isDeleted = true )
        replyToDelete.setDeleted(true);

        // 3. isDeleted 를 수정한 댓글을 repository 에 save
        Reply deletedReply = repository.save(convertToEntity(replyToDelete));

        // 4. 실제로 delete 한 DTO 를 반환.
        log.info("BoardServiceImpl.delete - deletedReply: " + deletedReply.toString());
        return convertToDto(deletedReply);
    }

    
}
