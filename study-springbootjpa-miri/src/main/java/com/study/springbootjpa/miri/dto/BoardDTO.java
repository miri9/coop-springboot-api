package com.study.springbootjpa.miri.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
 * List<Reply> replys
 * List<AttachFile> files
 * 
 * [domain 과 dto 의 차이점?]
 * - 예컨대 작성날짜 등의 값을 특정 포맷으로 바꿀 때 DTO 에서 이를 수행한다던가?
 * => 비즈니스 로직 외에 좀더 세부적인 작업들?
 */
// TODO : entity <-> DTO 변환 담당하는 로직 필요
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

    private String pw;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // 조인 데이터 보관용 필드
    private List<Reply> replys;
    // private List<AttachFile> files;
}
