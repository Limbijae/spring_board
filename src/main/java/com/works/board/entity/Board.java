package com.works.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String title;

    private String content;
}
