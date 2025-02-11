package com.example.miniProject.controller;

import com.example.miniProject.domain.Board;
import com.example.miniProject.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 생성자 주입 사용 (field injection 대신)
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 목록 조회 (페이지네이션 적용)
    @GetMapping
    public String listBoards(Model model,
                             @RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        model.addAttribute("boards", boardService.findAllBoards(page, pageSize));
        model.addAttribute("currentPage", page);
        return "board/list";
    }

    // 게시글 상세 조회, 게시글이 없을 경우 에러 메시지 처리
    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model, RedirectAttributes redirectAttributes) {
        Optional<Board> boardOpt = boardService.findById(id);
        if (boardOpt.isPresent()) {
            model.addAttribute("board", boardOpt.get());
            model.addAttribute("currentPage", page);
            return "board/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/boards";
        }
    }

    // 삭제 폼 화면
    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "board/deleteform";
    }

    // 게시글 삭제 (비밀번호 검증 후 삭제)
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id,
                         @RequestParam("password") String password,
                         RedirectAttributes redirectAttributes) {
        if (boardService.verifyPassword(id, password)) {
            boardService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
            return "redirect:/boards";
        }
        redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 틀렸습니다.");
        return "redirect:/boards/deleteform?id=" + id;
    }

    // 게시글 작성 폼 화면
    @GetMapping("/writeform")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/writeform";
    }

    // 게시글 작성 후 저장
    @PostMapping("/write")
    public String write(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        Board savedBoard = boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
        return "redirect:/boards/view?id=" + savedBoard.getId();
    }

    // 게시글 수정 폼 화면, 게시글 존재 여부 확인
    @GetMapping("/updateform")
    public String updateForm(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Board> boardOpt = boardService.findById(id);
        if (boardOpt.isPresent()) {
            model.addAttribute("board", boardOpt.get());
            return "board/updateform";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/boards";
        }
    }

    // 게시글 수정 (비밀번호 검증 후 업데이트)
    @PostMapping("/update")
    public String update(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        if (boardService.verifyPassword(board.getId(), board.getPassword())) {
            Board updatedBoard = boardService.update(board);
            redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
            return "redirect:/boards/view?id=" + updatedBoard.getId();
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않아 수정할 수 없습니다.");
            return "redirect:/boards/view?id=" + board.getId();
        }
    }
}