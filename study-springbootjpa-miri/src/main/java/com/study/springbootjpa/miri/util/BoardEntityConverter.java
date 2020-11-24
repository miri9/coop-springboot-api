package com.study.springbootjpa.miri.util;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;

/**
 * BoardEntityConverter Board entity,dto 치환 메서드를 가진 클래스 - convertToDto : board
 * domian -> board dto - convertToEntity : board dto -> board domain
 */
public class BoardEntityConverter {
        /**
     * DTO -> Entity
     * @param BoardDTO dto
     * @return Board
     */
    public Board convertToEntity(BoardDTO dto){
        // 만약 entity 의 id 값이 비어있다면 (null or long 자동 초기화 0) last index 쿼리 새로 만든다.
        return Board.builder()
                    .board_id(dto.getBoard_id() )
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(dto.getWriter())
                    .password(dto.getPassword())
                    .createdAt(dto.getCreatedAt())
                    .modifiedAt(dto.getModifiedAt())
                    // .replys(dto.getReplys()) // 타입 안맞음
                    // .files(dto.getFiles())
                    .build();
    }
    
    /**
     * Entity -> DTO
     * @param Board board
     * @return BoardDTO
     */
    public BoardDTO convertToDto(Board entity){
        return BoardDTO.builder()
                        .board_id(entity.getBoard_id())
                        .title(entity.getTitle())
                        .content(entity.getContent())
                        .writer(entity.getWriter())
                        .password(entity.getPassword())
                        .createdAt(entity.getCreatedAt())
                        .modifiedAt(entity.getModifiedAt())
                        // .replys(entity.getReplys())
                        // .files(entity.getFiles())
                        .build();
    }
}
