package com.study.springbootjpa.miri.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
 * List<ReplyDTO> replys
 * List<AttachFileDTO> files
 * 
 * [조인 데이터를 dto 화 하는 로직]
 * convertReplysToDto : 인자 List<Reply> -> 반환 List<ReplyDTO>
 * 
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
    private List<ReplyDTO> replys;
    // private List<AttachFileDTO> files;

    /**
     * List<Reply> 를 받아, this.replys (List<ReplyDTO>) 로 전환
     * @param entityList
     */
    public void convertReplysToDto(List<Reply> entityList){
        List<ReplyDTO> result = new ArrayList<>();
        entityList.forEach(entity ->{
            ReplyDTO dto = ReplyDTO.builder()
                .reply_id(entity.getReply_id())
                .board(entity.getBoard().getBoard_id())
                .replyer(entity.getReplyer())
                .rpely_password(entity.getRpely_password())
                .reply_content(entity.getReply_content())
                .createdAt(entity.getCreatedAt())
                .build();
            result.add(dto);
        });
        return result;
    }
}
