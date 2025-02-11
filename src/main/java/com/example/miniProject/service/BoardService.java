package com.example.miniProject.service;


import com.example.miniProject.domain.Board;
import com.example.miniProject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;


    public Page<Board> findAllBoards(int page, int pageSize){
        Pageable pageable = PageRequest.of(page-1,pageSize,Sort.by(Sort.Direction.DESC, "createdAt"));
        return boardRepository.findAll(pageable);

    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean verifyPassword(Long id, String password) {
        Optional<Board> board = findById(id);
        return board.map(b -> b.getPassword().equals(password)).orElse(false);
    }

    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }


    @Transactional
    public Board update(Board board) {
        return boardRepository.save(board);
    }
}