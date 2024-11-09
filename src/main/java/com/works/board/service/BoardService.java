package com.works.board.service;

import com.works.board.entity.Board;
import com.works.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired //스프링 빈이 의존성주입 해줌
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void boardWrite(Board board) {

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();  //findById로 쓰면 옵셔널 값으로 받아와서 .get() 해줘야함

    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){

        boardRepository.deleteById(id);

    }
}
