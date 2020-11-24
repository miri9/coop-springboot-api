package com.study.springbootjpa.miri.service;

import java.util.List;

import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;

/**
 * ReplyService
 * 
 * by miri
 * 
 * 기본 댓글의 CRUD 를 가진 댓글 인터페이스
 * 
 * [비즈니스 메서드] - read : id 로 단일 댓글 가져오기 - getList : 특정 게시판에 해당하는 모든 댓글 가져오기 (페이징X
 * 경우 : 현재 이것만 구현) 모든 데이터를 뿌려준다. - insert : 화면으로부터 dto를 받아, DB 에 삽입 - update :
 * 화면으로부터 dto를 받아, DB 에 업데이트 - delete : 화면으로투버 id를 받아, DB 에서 제거
 * 
 * [entity,dto 관련 메서드] - convertToDto : domian -> dto - convertToEntity : dto ->
 * domain
 */
public interface ReplyService {
    
    public ReplyDTO read(Long id);

    // 페이지네이션 적용X
    public List<ReplyDTO> getList();

    public Reply insert(ReplyDTO reply);
    public Reply update(ReplyDTO reply);
    public Reply delete(Long id);

    // /**
    //  * DTO -> Entity
    //  * @param ReplyDTO dto
    //  * @return Reply
    //  */
    // public default Reply convertToEntity(ReplyDTO dto){
    //     return Reply.builder()
    //                 .reply_id(dto.getReply_id())
    //                 // .board(dto.getBoard()) // 타입 안맞음
    //                 .replyer(dto.getReplyer())
    //                 .rpely_password(dto.getRpely_password())
    //                 .reply_content(dto.getReply_content())
    //                 .createdAt(dto.getCreatedAt())
    //                 .build();
    // }
    // /**
    //  * Entity -> DTO
    //  * @param ReplyDTO dto
    //  * @return Reply
    //  */
    // public default Reply convertToDto(Reply entity){
    //     return Reply.builder()
    //                 .reply_id(entity.getReply_id())
    //                 // .board(entity.getBoard())
    //                 .replyer(entity.getReplyer())
    //                 .rpely_password(entity.getRpely_password())
    //                 .reply_content(entity.getReply_content())
    //                 .createdAt(entity.getCreatedAt())
    //                 .build();
    // }


    
}
