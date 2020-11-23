package com.study.springbootjpa.miri.domain;

import java.time.LocalDateTime;

import javax.persistence.Id;

/**
 * Domain Reply
 * 
 * by miri
 * 
 * [기본 필드]
 * Long reply_id
 * Board board : 댓글이 속한 board (association)
 * String replyer
 * String reply_pw
 * String reply_content
 * LocalDateTie createdAt
 * : 다른 사이트 예제 보면 수정 시간 따로 표기 안하는 경우 많으므로 수정 시간은 제외했음.
 */
 // TODO : validation 과 jpa association
 // TODO : 비즈니스 로직 메서드 확인

public class Reply {
    @Id
    private Long reply_id;

    private Board board; // asso

    private String replyer;

    private String rpely_pw;

    private String reply_content;

    private LocalDateTime createdAt;
}
