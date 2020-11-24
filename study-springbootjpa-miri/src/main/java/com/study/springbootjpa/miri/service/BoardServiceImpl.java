package com.study.springbootjpa.miri.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.domain.Reply;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.dto.ReplyDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;
import com.study.springbootjpa.miri.repository.ReplyRepository;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
/**
 * TODO : entity domain 서로 변환할때마다 .. 각 참조 객체는 따로 변환해주기. 
 */
@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

    // @Autowired
    // private BoardRepository repository;
    
    //생성자 주입
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;
    public BoardServiceImpl(BoardRepository repository,ReplyRepository replyRepository){
        this.repository = repository;
        this.replyRepository = replyRepository;
    }

    @Transactional
    @Override
    public BoardDTO read(Long id) {
        // board 엔티티 가져오기
        Board board = repository.getBoardWithReply(id);

        // board 엔티티의 reply 엔티티 가져오기
        List<Reply> reply = board.getReplys();

        // board 엔티티를 dto 로 변환
        BoardDTO boardDto = convertToDto(board);

        // reply 엔티티를 dto 로 변환
        List<ReplyDTO> replyDtos = new ArrayList<>();
        reply.forEach(replyEntity -> {
            convertToDto(replyEntity);

        });

        BoardDTO dto = convertToDto(repository.getBoardWithReply(id));

        // return convertToDto(board);
        return null;




    }

    @Override
    public List<BoardDTO> getList() {
        List<Board> boards = repository.findAll();
        List<BoardDTO> dtos = boards.stream().map(i -> convertToDto(i)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public Board insert(BoardDTO board) {
        
        // TODO : 유효성 검사 후 board 로 변경후 save
        
        
        Board boardAfterInsert = repository.save(convertToEntity(board));
        // Board boardAfterInsert = convertToEntity(board);
        log.info("boardAfterInsert : ...."+boardAfterInsert.toString());

        return boardAfterInsert;
    }

    /**
     * TODO : update 시 기존 엔티티와 새 엔티티 내용 검사
     */
    @Override
    public Board update(BoardDTO board) {
        // Board boardAfterUpdate = repository.update(board);
        // 인자 board : 새로 업데이트할 내용 담은 board

        Board boardToUpdate = repository.getOne(board.getBoard_id()); // boardToUpdate : 기존 board
        Board boardAfterUpdate = repository.save(boardToUpdate); // boardAfterUpdate : 업데이트 후 board
        return boardAfterUpdate;
    }

    /**
     * TODO : delete 시 내용이 제대로 삭제되었는지 확인
     */
    @Override
    public Board delete(Long id){
        Board boardToDelete = repository.getOne(id); // boardToDelete : 삭제할 board

        repository.delete(boardToDelete); //boardAfterDelete : 삭제 후 board

        return boardToDelete;
    }

}
