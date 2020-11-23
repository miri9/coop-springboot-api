package com.study.springbootjpa.miri.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Domain Board
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
 * [조인 데이터 위한 프리징 필드]
 * List<Reply> replys
 * List<AttachFile> files
 */

 // TODO : validation 과 jpa association
 // TODO : 비즈니스 로직 메서드 추가
@Entity
@Table(name = "tbl_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {
    @Id
    private Long board_id;

    private String title;

    private String content;

    private String writer;

    private String pw;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // 조인 데이터 가져오기용 필드
    private List<Reply> replys;
    // private List<AttachFile> files;

}
