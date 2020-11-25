package com.study.springbootjpa.miri.service;


import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

    //생성자 주입
    private final BoardRepository repository;
    public BoardServiceImpl(BoardRepository repository){
        this.repository = repository;
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
    public List<BoardDTO> getList() {
        // 페이징 X
        List<Board> boards = repository.getBoardListWithReply();

        List<BoardDTO> dtos = boards.stream().map(i -> convertToDto(i)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public BoardDTO insert(BoardDTO board) {
        
        Board boardAfterInsert = repository.save(convertToEntity(board));
        
        log.info("BoardServiceImpl.insert - boardAfterInsert: "+boardAfterInsert.toString());

        return convertToDto(boardAfterInsert);
    }

    /**
     * TODO : update 시 기존 엔티티와 새 엔티티 내용 검사
     */
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
