package com.study.springbootjpa.miri.dto;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

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
 * Long replyId
 * Long board
 * String replyer
 * String replyPw
 * String replyContent
 * LocalDateTie createdAt : 다른 사이트 예제 보면 수정 시간 따로 표기 안하는 경우 많으므로
 * 수정 시간은 제외했음.
 * boolean isDeleted : 기본값 false. 댓글 삭제 시 true 이다.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReplyDTO {
    @Id
    private Long replyId;

    private Long board;

    private String replyer;

    private String replyPassword;

    private String replyContent;

    // @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")

    private LocalDateTime createdAt;
    private boolean isDeleted;

}
