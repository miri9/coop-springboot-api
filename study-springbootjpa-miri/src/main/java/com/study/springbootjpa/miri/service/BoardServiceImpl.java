package com.study.springbootjpa.miri.service;


import java.util.List;

import com.study.springbootjpa.miri.domain.Board;
import com.study.springbootjpa.miri.dto.BoardDTO;
import com.study.springbootjpa.miri.repository.BoardRepository;

import org.springframework.stereotype.Service;
/**
 * TODO : 게시글과 관련된 댓글도 가져오기
 */
@Service
public class BoardServiceImpl implements BoardService {

    //생성자 주입
    private final BoardRepository repository;
    public BoardServiceImpl(BoardRepository repository){
        this.repository = repository;
    }

    @Override
    public BoardDTO read(Long id) {
        Board board = repository.findById(id).get();
        return null;
    }

    @Override
    public List<BoardDTO> getList() {
        List<Board> boards = repository.findAll();
        return null;
    }

    @Override
    public BoardDTO insert(BoardDTO board) {
        return null;
    }

    @Override
    public BoardDTO update(BoardDTO board) {
        return null;
    }

    @Override
    public BoardDTO delete(BoardDTO board) {
        return null;
    }

}
