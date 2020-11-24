package com.study.springbootjpa.miri.dto;

import java.time.LocalDateTime;

import com.study.springbootjpa.miri.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO Reply
 * 
 * by miri
 * 
 * [기본 필드]
 * Long reply_id
 * // BoardDTO board
 * String replyer
 * String reply_pw
 * String reply_content
 * LocalDateTie createdAt
 * : 다른 사이트 예제 보면 수정 시간 따로 표기 안하는 경우 많으므로 수정 시간은 제외했음.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReplyDTO {
    private Long reply_id;

    private Long board;

    private String replyer;

    private String rpely_password;

    private String reply_content;

    private LocalDateTime createdAt;


}
