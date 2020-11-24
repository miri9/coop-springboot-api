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

    // @Autowired
    // private BoardRepository repository;
    
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

        Board boardToUpdate = repository.getBoardWithReply(board.getBoard_id()); // boardToUpdate : 기존 board
        Board boardAfterUpdate = repository.save(boardToUpdate); // boardAfterUpdate : 업데이트 후 board

        return convertToDto(boardAfterUpdate);
    }

    /**
     * TODO : delete 시 board 와 reply 모두 삭제되는지 확인
     */
    @Override
    public BoardDTO delete(Long id){
        Board boardToDelete = repository.getBoardWithReply(id); // boardToDelete : 삭제할 board

        Board boardDeleted = repository.delete(id); //boardAfterDelete : 삭제 후 board

        return convertToDto(boardDeleted);
    }

}
