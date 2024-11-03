package com.works.board.repository;

import com.works.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer> {

}
