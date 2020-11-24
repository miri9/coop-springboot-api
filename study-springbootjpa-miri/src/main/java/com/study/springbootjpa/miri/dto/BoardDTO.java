package com.study.springbootjpa.miri.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Id;

import com.study.springbootjpa.miri.domain.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * DTO Board
 * 
 * by miri
 * 
 * [기본 필드]
 * Long board_id
 * String title
 * String content
 * String writer : 익명 멤버 (시큐리티 도입 시 수정 필요)
 * String pw : 익명 멤버 (시큐리티 도입 시 수정 필요)
 * LocalDateTime createdAt, modifiedAt : 작성 및 수정 시간
 * 처음 작성 시 modifiedAt = createdAt 의 값
 * 
 * [조인 데이터를 화면에 뿌리기 위한 필드]
 * Board.Reply.Board ... 순환 참조 막고자 @Nullable 넣음
 * List<ReplyDTO> replys
 * List<AttachFileDTO> files
 * 
 * [사용자 정의 setter]
 * - addReply : dto <-> entity 전환 메서드에서 사용. replys 에 reply 를 하나씩 추가
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDTO {
    @Id
    private Long board_id;

    private String title;

    private String content;

    private String writer;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // 조인 데이터 보관용 필드
    @Nullable
    private List<ReplyDTO> replys;
    // private List<AttachFileDTO> files;

    public void addReply(ReplyDTO dto){
        this.replys.add(dto);
    }

}
