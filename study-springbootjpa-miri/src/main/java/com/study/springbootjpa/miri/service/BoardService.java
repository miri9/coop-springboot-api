package com.study.springbootjpa.miri.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.dto.ReplyDTO;

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
 * (페이징O 경우 : 미완성) CurrentPage 와 Pagination 을 이용해 페이징된 데이터를 보내준다.
 * - insert : 화면으로부터 dto 를 받아, DB 에 삽입
 * - update : 화면으로부터 dto 를 받아, 해당 DB 로 업데이트
 * - delete : 화면으로부터 id를 받아, 해당 board 를 DB 에서 제거 
 * 
 * [entity,dto 치환 메서드]
 * - convertToDto : board domian -> board dto
 * - convertToEntity : board dto -> board domain
 * 
 * [코드 제한]
 * 모든 메서드는 해당 작업을 거친 결과물을 다시 반환하여야 한다.
 */
public interface BoardService {

    public BoardDTO read(Long id);
    
    // 페이지네이션 적용 X
    public List<BoardDTO> getList();

	public BoardDTO insert(BoardDTO board);
    public BoardDTO update(BoardDTO board);
    public boolean delete(Long id);

    /**
     * DTO -> Entity
     * @param BoardDTO dto
     * @return Board
     */
    public default Board convertToEntity(BoardDTO dto){
        // 1. BoardDTO.replys(ReplyDTO) -> replys(Reply)
        List<Reply> replys = new ArrayList<>();
        dto.getReplys().forEach(i -> {
            replys.add(
                Reply.builder()
                .reply_id(i.getReply_id())
                .replyer(i.getReplyer())
                .rpely_password(i.getRpely_password())
                .reply_content(i.getReply_content())
                .createdAt(i.getCreatedAt())
                .board(dto.getBoard_id())
                .build());
        });
        // 2.BoardDTO -> Board : Board.replys 에 1번 변수 replys 인서트
        return Board.builder()
                .board_id(dto.getBoard_id())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .password(dto.getPassword())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .replys(replys)
                .build();


    }
    
    /**
     * Entity -> DTO
     * @param Board board
     * @return BoardDTO
     */
    public default BoardDTO convertToDto(Board entity){
        // 1. Board.replys -> replys(ReplyDTO)
        List<ReplyDTO> replys = new ArrayList<>();
        entity.getReplys().forEach(i -> {
            replys.add(
                ReplyDTO.builder()
                .reply_id(i.getReply_id())
                .replyer(i.getReplyer())
                .rpely_password(i.getRpely_password())
                .reply_content(i.getReply_content())
                .createdAt(i.getCreatedAt())
                .board(entity.getBoard_id())
                .build());
        });

        // 2. Board -> BoardDTO : BoardDTO.replys 에 1번 변수 replys 인서트
        return BoardDTO.builder()
                .board_id(entity.getBoard_id())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .replys(replys)
                .build();

    }
    
}
