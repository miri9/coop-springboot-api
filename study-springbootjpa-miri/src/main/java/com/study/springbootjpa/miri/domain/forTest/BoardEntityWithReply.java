package com.study.springbootjpa.miri.domain.forTest;

import java.util.List;

import com.study.springbootjpa.miri.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO Board
 * 
 * by miri
 * 
 * [기본 필드] Long boardId String title String content String writer : 익명 멤버 (시큐리티
 * 도입 시 수정 필요) String pw : 익명 멤버 (시큐리티 도입 시 수정 필요) LocalDateTime createdAt,
 * modifiedAt : 작성 및 수정 시간 처음 작성 시 modifiedAt = createdAt 의 값 boolean isDeleted
 * : 기본값 false. 게시글 삭제 시 true 이다.
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardEntityWithReply extends BoardEntity{

     // 조인 데이터 보관용 필드
    private List<ReplyDTO> replys;
    // private List<AttachFileDTO> files;

    public void addReply(ReplyDTO dto){
        this.replys.add(dto);
    }
    public void addReply(List<ReplyDTO> dtos){
        dtos.forEach(replyDto -> this.replys.add(replyDto));
    }

}
