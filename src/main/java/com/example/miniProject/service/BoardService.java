package com.example.miniProject.service;

import com.example.miniProject.domain.Board;
import com.example.miniProject.repository.BoardRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 생성자 주입 사용
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 페이징된 게시글 목록 조회
    public Page<Board> findAllBoards(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        return boardRepository.findAll(pageable);
    }

    // ID로 게시글 조회
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 비밀번호 검증
    public boolean verifyPassword(Long id, String password) {
        return findById(id)
                .map(b -> b.getPassword().equals(password))
                .orElse(false);
    }

    // 새 게시글 저장 (생성 및 수정 시간 설정)
    @Transactional
    public Board saveBoard(Board board) {
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        return boardRepository.save(board);
    }

    // 기존 게시글 수정 (수정 가능한 필드만 업데이트하고, updatedAt 갱신)
    @Transactional
    public Board update(Board board) {
        Optional<Board> optionalBoard = boardRepository.findById(board.getId());
        if (optionalBoard.isPresent()) {
            Board existingBoard = optionalBoard.get();
            existingBoard.setName(board.getName());
            existingBoard.setTitle(board.getTitle());
            existingBoard.setContent(board.getContent());
            existingBoard.setUpdatedAt(LocalDateTime.now()); // 수정 시간 갱신
            return boardRepository.save(existingBoard);
        }
        throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id: " + board.getId());
    }
}