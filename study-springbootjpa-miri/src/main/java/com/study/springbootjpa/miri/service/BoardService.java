package com.study.springbootjpa.miri.service;


import java.util.List;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;

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
 * - insert : 화면으로부터 dto 를 받아, 유효성 검사 후 DB 에 삽입
 * - update : 화면으로부터 dto 를 받아, 해당 DB 로 업데이트
 * - delete : 화면으로부터 id를 받아, 해당 board 를 DB 에서 제거 
 * 
 * [entity,dto 관련 메서드]
 * - convertToDto : domian -> dto
 * - convertToEntity : dto -> domain
 */
public interface BoardService {

    public BoardDTO read(Long id);
    
    // 페이지네이션 적용 X
    public List<BoardDTO> getList();

	public BoardDTO insert(BoardDTO board);
    public BoardDTO update(BoardDTO board);
    public BoardDTO delete(BoardDTO board);

    /**
     * DTO -> Entity
     * @param BoardDTO dto
     * @return Board
     */
    public default Board convertToEntity(BoardDTO dto){
        return Board.builder()
                    .board_id(dto.getBoard_id())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(dto.getWriter())
                    .pw(dto.getPw())
                    .createdAt(dto.getCreatedAt())
                    .modifiedAt(dto.getModifiedAt())
                    .replys(dto.getReplys())
                    // .files(dto.getFiles()) // 현재 사용하지 않음
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
                        .pw(entity.getPw())
                        .createdAt(entity.getCreatedAt())
                        .modifiedAt(entity.getModifiedAt())
                        .replys(entity.getReplys())
                        // .files(entity.getFiles()) // 현재 사용하지 않음
                        .build();
    }
    
}