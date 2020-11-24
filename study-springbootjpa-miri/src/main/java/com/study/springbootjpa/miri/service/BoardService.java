package com.study.springbootjpa.miri.service;


import java.util.List;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;

import org.springframework.stereotype.Service;

/**
 * BoardService 
 * by miri
 * 
 * 기본 게시판의 CRUD 를 가진 서비스 인터페이스
 * 
 * [비즈니스 메서드]
 * - read : id 로 단일 게시물 가져오기
 * - getList : 특정 페이지에 해당하는 board 의 리스트 가져온다.
 * (페이징X 경우 : 현재 이것만 구현) 모든 데이터를 뿌려준다.
 * (페이징O 경우) CurrentPage 와 Pagination 을 이용해 페이징된 데이터를 보내준다.
 * - insert : 화면으로부터 dto 를 받아, DB 에 삽입
 * - update : 화면으로부터 dto 를 받아, 해당 DB 로 업데이트
 * - delete : 화면으로부터 id를 받아, 해당 board 를 DB 에서 제거 
 */
public interface BoardService {

    // 주입 : OOOentityConverter => entity,dto 간 컨버터

    public BoardDTO read(Long id);
    
    // 페이지네이션 적용 X
    public List<BoardDTO> getList();

	public Board insert(BoardDTO board);
    public Board update(BoardDTO board);
    public Board delete(Long id);

    /**
     * DTO -> Entity
     * @param BoardDTO dto
     * @return Board
     */
    public default Board convertToEntity(BoardDTO dto){
        // 만약 entity 의 id 값이 비어있다면 (null or long 자동 초기화 0) last index 쿼리 새로 만든다.
        return Board.builder()
                    .board_id(dto.getBoard_id() )
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(dto.getWriter())
                    .password(dto.getPassword())
                    .createdAt(dto.getCreatedAt())
                    .modifiedAt(dto.getModifiedAt())
                    // .replys(dto.getReplys()) // 타입 안맞음. replyDTO 를 replyEntity 화 해야함.
                    // .files(dto.getFiles())
                    .build();
    }
    
    /**
     * Entity -> DTO
     * @param Board board
     * @return BoardDTO
     */
    public default BoardDTO convertToDto(Board entity){
        return BoardDTO.builder()
                        .board_id(entity.getBoard_id())
                        .title(entity.getTitle())
                        .content(entity.getContent())
                        .writer(entity.getWriter())
                        .password(entity.getPassword())
                        .createdAt(entity.getCreatedAt())
                        .modifiedAt(entity.getModifiedAt())
                        .replys(entity.getReplys()) // 댓글 entity -> dto
                        // .files(entity.getFiles())
                        .build();
    }
    
}
