package com.study.springbootjpa.miri.service;

import java.util.ArrayList;
import java.util.List;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.dto.ReplyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
 * - delete : 화면으로부터 id를 받아, 해당 board.isDeleted = true;
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

    /**
     * 페이징과 정렬 제외하고, 모든 게시글 리스트 가져오기
     * @param 
     * @return List<BoardDTO>
     * 
     * @Transactional :
     * org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.study.springbootjpa.miri.domain.Board.replys, could not initialize proxy - no Session
     */
    public List<BoardDTO> getList();
    /**
     * 페이징과 정렬 포함한 게시글 리스트 가져오기
     * @param Pageable pageable
     * @return Page<BoardDTO>
     * 
     * @Transactional :
     * org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.study.springbootjpa.miri.domain.Board.replys, could not initialize proxy - no Session
     */
    public Page<BoardDTO> getList(Pageable pageable);

	public BoardDTO insert(BoardDTO board);
    public BoardDTO update(BoardDTO board);
    public BoardDTO delete(Long id);

    /**
     * DTO -> Entity
     * @param BoardDTO dto
     * @return Board
     */
    public default Board convertToEntity(BoardDTO dto){
        // 1. BoardDTO.replys(ReplyDTO) -> replys(Reply)
        List<Reply> replys = new ArrayList<>();
        boolean haveReplys = dto.getReplys()!=null;

        if(haveReplys){
            dto.getReplys().forEach( (ReplyDTO i) -> { // i = 각 ReplyDTO
                replys.add(
                    Reply.builder()
                    .replyId(i.getReplyId())
                    .replyer(i.getReplyer())
                    .replyPassword(i.getReplyPassword())
                    .replyContent(i.getReplyContent())
                    .createdAt(i.getCreatedAt())
                    .isDeleted(i.isDeleted())
                    .board(dto.getBoardId())
                    .build());
            });
        } 
        
        // 2.BoardDTO -> Board : Board.replys 에 1번 변수 replys 인서트
        return Board.builder()
                .boardId(dto.getBoardId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .password(dto.getPassword())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .isDeleted(dto.isDeleted())
                .replys(haveReplys?replys:new ArrayList<>())
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
        
        entity.getReplys().forEach( (Reply i) -> { // i = Reply
            replys.add(
                ReplyDTO.builder()
                .replyId(i.getReplyId())
                .replyer(i.getReplyer())
                .replyPassword(i.getReplyPassword())
                .replyContent(i.getReplyContent())
                .createdAt(i.getCreatedAt())
                .isDeleted(i.isDeleted())
                .board(entity.getBoardId())
                .build());
        });

        // 2. Board -> BoardDTO : BoardDTO.replys 에 1번 변수 replys 인서트
        return BoardDTO.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .isDeleted(entity.isDeleted())
                .replys(replys)
                .build();

    }

}
