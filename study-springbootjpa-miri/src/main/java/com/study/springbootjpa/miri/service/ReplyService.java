package com.study.springbootjpa.miri.service;

import java.util.List;

import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;

/**
 * ReplyService
 * 
 * by miri
 * 
 * 기본 댓글의 CRUD 를 가진 인터페이스
 * 
 * [비즈니스 메서드]
 * - read : 댓글 id 로 단일 댓글 가져오기
 * - getList : 특정 게시판 id에 해당하는 모든 댓글 가져오기
 * (페이징X 경우 : 현재 이것만 구현) 모든 데이터를 뿌려준다.
 * (페이징O 경우 : 미완성)
 * - insert : 화면으로부터 dto를 받아, DB 에 삽입
 * - update : 화면으로부터 dto를 받아, DB 에 업데이트
 * - delete : 화면으로투버 id를 받아, 해당 reply.isDeleted = true;
 * 
 * [entity,dto 치환 메서드]
 * - convertToDto : domian -> dto
 * - convertToEntity : dto -> domain
 * 
 * [코드 제한]
 * 모든 메서드는 해당 작업을 거친 결과물을 다시 반환하여야 한다.
 */

public interface ReplyService {
    
    public ReplyDTO read(Long replyId);

    /**
     * 페이지네이션 적용 X.
     * @param Long board_id
     */
    public List<ReplyDTO> getList(Long boardId);

    public ReplyDTO insert(ReplyDTO reply);
    public ReplyDTO update(ReplyDTO reply);

    /**
     * @param Long reply_id
     */
    public ReplyDTO delete(Long reply_id);

    /**
     * DTO -> Entity
     * @param ReplyDTO dto
     * @return Reply
     */
    public default Reply convertToEntity(ReplyDTO dto){
        

        // ReplyDTO -> Reply : Reply.board 에 board_id 할당
        Reply replyEntity = Reply.builder()
                            .replyId(dto.getReplyId())
                            .replyer(dto.getReplyer())
                            .replyPassword(dto.getReplyPassword())
                            .replyContent(dto.getReplyContent())
                            .createdAt(dto.getCreatedAt())
                            .isDeleted(dto.isDeleted())                            
                            .board(dto.getBoard())
                            .build();
        return replyEntity;
    }
    /**
     * Entity -> DTO
     * @param ReplyDTO dto
     * @return Reply
     */
    public default ReplyDTO convertToDto(Reply entity){
        // Reply -> ReplyDTO : ReplyDTO.board 에 board_id 할당
        return ReplyDTO.builder()
                        .replyId(entity.getReplyId())
                        .replyer(entity.getReplyer())
                        .replyPassword(entity.getReplyPassword())
                        .replyContent(entity.getReplyContent())
                        .createdAt(entity.getCreatedAt())
                        .isDeleted(entity.isDeleted())                            
                        .board(entity.getBoard())
                        .build();
    }


    
}
