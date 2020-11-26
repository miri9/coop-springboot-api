package com.study.springbootjpa.miri.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    /**
     * Page 객체 얻어오는 repository 쿼리 날릴 때, 왜 Transaction 필요할까?
     * Page 쿼리 + Page Count 쿼리가 논리적으로 같은 단위의 작업이어서?
     */


    private final BoardRepository repository;
    private final Function<Board,BoardDTO> boardPageConveter; // Page<Board> -> Page<BoardDTO> 변환 위함
    public BoardServiceImpl(BoardRepository repository){
        this.repository = repository;
        this.boardPageConveter = new Function<Board,BoardDTO>(){
            @Override
            public BoardDTO apply(Board board) {
                return convertToDto(board);
            }
        };
    }

    @Transactional
    @Override
    public BoardDTO read(Long id) {
        // board 엔티티 가져오기
        Board board = repository.getBoardWithReply(id);

        // board 엔티티, board.reply 엔티티를 dto 로 변환
        BoardDTO boardDto = convertToDto(board);

        return boardDto;
    }

    @Override
    @Transactional
    public List<BoardDTO> getList() {
        // 페이징과 정렬 X
        List<Board> boards = repository.getBoardListWithReply();

        List<BoardDTO> dtos = boards.stream().map(i -> convertToDto(i)).collect(Collectors.toList());
        return dtos;
    }


    @Override
    @Transactional
    public Page<BoardDTO> getList(Pageable pageable) {
        Page<Board> boardPages = repository.getBoardListWithReply(pageable);

        // Page<Board> -> Page<BoardDTO> 로 변환
        Page<BoardDTO> boardDtoPages = boardPages.map(board -> boardPageConveter.apply(board));

        return null;
    }

    @Override
    public BoardDTO insert(BoardDTO board) {
        Board boardAfterInsert = repository.save(convertToEntity(board));

        
        log.info("BoardServiceImpl.insert - boardAfterInsert: "+boardAfterInsert.toString());

        return convertToDto(boardAfterInsert);
    }

    @Override
    public BoardDTO update(BoardDTO board) {
        // 인자 board : 새로 업데이트할 내용 담은 board
        
        log.info("BoardServiceImpl.update - boardToUpdate (param DTO): "+board.toString());
        
        Board boardAfterUpdate = repository.save(convertToEntity(board)); // boardAfterUpdate : 업데이트 후 board
        
        log.info("BoardServiceImpl.update - boardAfterUpdate: "+boardAfterUpdate.toString());

        return convertToDto(boardAfterUpdate);
    }

    @Override
    public BoardDTO delete(Long id){

        // 1. 삭제할 게시물 가져오기
        BoardDTO boardToDelete = convertToDto(repository.getBoardWithReply(id));
        // 2. 댓글 삭제 ( isDeleted = true )
        boardToDelete.getReplys().forEach(replyDto -> replyDto.setDeleted(true));
        // 3. 게시글 삭제 ( isDeleted = true ) : 댓글의 상태는 이미 isDeleted = true 여야 함.
        boardToDelete.setDeleted(true);

        // 4. 댓글과 게시글의 isDeleted = true 이면, repository 에 save
        Board deletedBoard = repository.save(convertToEntity(boardToDelete));
        
        // 5. 실제로 delete 한 DTO 를 반환   
        log.info("BoardServiceImpl.update - 실제로 삭제된 deletedBoard: "+deletedBoard.toString());
        return convertToDto(deletedBoard);
    }

}
