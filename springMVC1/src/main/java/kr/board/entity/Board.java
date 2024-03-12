package kr.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    
    private int idx;
    private String title;
    private String content;
    private String writer;
    private String indate;
    private int count;
    
}
