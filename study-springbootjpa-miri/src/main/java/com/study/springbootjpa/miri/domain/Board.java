package com.study.springbootjpa.miri.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * Domain Board
 * 
 * by miri
 * 
 * [기본 필드]
 * Long boardId
 * String title
 * String content
 * String writer : 익명 멤버 (시큐리티 도입 시 수정 필요)
 * String pw : 익명 멤버 (시큐리티 도입 시 수정 필요)
 * LocalDateTime createdAt, modifiedAt : 작성 및 수정 시간
 * 처음 작성 시 modifiedAt = createdAt 의 값
 * boolean isDeleted : 기본값 false. 게시글 삭제 시 true 이다.
 * 
 * [조인 데이터 위한 프리징 필드]
 * List<Reply> replys
 * List<AttachFile> files
 * 
 * [조인 데이터를 entity 화 하는 로직]
 * convertReplysToEntity : 인자 List<ReplyDTO> -> 반환 List<Reply>
 */

 // TODO : validation 과 jpa association
 // TODO : 비즈니스 로직 메서드 추가
 // TODO : TOString 관련 (프로토타입, 주소값만 보이기)
 // TODO : min,max 설정 걸기
@Entity
@Table(name = "tbl_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = "replys")// 조인 데이터 순환 참조 문제
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    private String title;

    private String content;

    private String writer;

    private String password;

    // @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private boolean isDeleted;

    /**
     * 조인 데이터 가져오기용 필드
     * // mappedBy : The field that owns the relationship. Required unless the relationship is unidirectional(양방향일 때 필요).
     * // cascade : (Optional) The operations that must be cascaded to the target of the association. Defaults to no operations being cascaded. When the target collection is a java.util.Map, the cascade element applies to the map value
     */
    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Reply> replys;

    // private List<AttachFile> files;

}
