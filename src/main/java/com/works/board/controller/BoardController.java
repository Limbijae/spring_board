package com.works.board.controller;

import com.works.board.entity.Board;
import com.works.board.service.BoardService;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @Autowired //스프링 빈이 의존성주입 해줌
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model){

        boardService.boardWrite(board);

        model.addAttribute("message", "글 작성이 완료되었습니다."); //글작성 시 alert 띄우기용
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){

        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, @RequestParam(name = "id")  Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam(name = "id") Integer id){
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model){

        Board boardTemp = boardService.boardView(id); //기존에 작성된 글
        boardTemp.setTitle(board.getTitle());         //새로 작성한글로 덮어씌우기
        boardTemp.setContent(board.getContent());     //새로 작성한글로 덮어씌우기

        boardService.boardWrite(boardTemp); //새로 작성한 내용으로 저장

        model.addAttribute("message", "수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
//        return "redirect:/board/list"; 수정완료 alert 안쓸때는 리다이렉트로 전달
    }
}
