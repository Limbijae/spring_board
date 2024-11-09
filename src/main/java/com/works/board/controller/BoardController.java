package com.works.board.controller;

import com.works.board.entity.Board;
import com.works.board.service.BoardService;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired //스프링 빈이 의존성주입 해줌
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam(name="file", required = false) MultipartFile file) throws Exception{

        boardService.boardWrite(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다."); //글작성 시 alert 띄우기용
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(name = "searchKeyword", defaultValue = "")  String searchKeyword) {

        Page<Board> list = null;

        //검색기능 사용유무
        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1 );                 // 초기값이 -가 나오지않게 max 사용
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // 마지막값이 총 페이지수를 넘지않게 조절

        model.addAttribute("list"     , list );
        model.addAttribute("nowPage"  , nowPage );
        model.addAttribute("startPage", startPage );
        model.addAttribute("endPage"  , endPage);

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
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, @RequestParam(name="file", required = false) MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id); //기존에 작성된 글
        boardTemp.setTitle(board.getTitle());         //새로 작성한글로 덮어씌우기
        boardTemp.setContent(board.getContent());     //새로 작성한글로 덮어씌우기

        boardService.boardWrite(boardTemp, file); //새로 작성한 내용으로 저장

        model.addAttribute("message", "수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
//        return "redirect:/board/list"; 수정완료 alert 안쓸때는 리다이렉트로 전달
    }
}
