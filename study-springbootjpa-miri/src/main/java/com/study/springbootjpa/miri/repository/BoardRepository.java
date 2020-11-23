package com.study.springbootjpa.miri.repository;

import com.study.springbootjpa.miri.domain.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    
}
