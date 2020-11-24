package com.study.springbootjpa.miri.util;

import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.ReplyDTO;

/**
 * ReplyEntityConverter Reply entity,dto 치환 메서드를 가진 클래스 - convertToDto : reply
 * domian -> reply dto - convertToEntity : reply dto -> reply domain
 */
public class ReplyEntityConverter {

    /**
     * DTO -> Entity
     * @param ReplyDTO dto
     * @return Reply
     */
    public Reply convertToEntity(ReplyDTO dto){
        return Reply.builder()
                    .reply_id(dto.getReply_id())
                    // .board(dto.getBoard()) // 타입 안맞음
                    .replyer(dto.getReplyer())
                    .rpely_password(dto.getRpely_password())
                    .reply_content(dto.getReply_content())
                    .createdAt(dto.getCreatedAt())
                    .build();
    }
    /**
     * Entity -> DTO
     * @param ReplyDTO dto
     * @return Reply
     */
    public Reply convertToDto(Reply entity){
        return Reply.builder()
                    .reply_id(entity.getReply_id())
                    // .board(entity.getBoard())
                    .replyer(entity.getReplyer())
                    .rpely_password(entity.getRpely_password())
                    .reply_content(entity.getReply_content())
                    .createdAt(entity.getCreatedAt())
                    .build();
    }
}
