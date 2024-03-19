package kr.co.sboard.dto;

import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ArticleDTO {
    private int no;
    private int parent;
    private int comment;
    private String cate;
    private String title;
    private String content;
    private int file;
    private int hit;
    private User user;
    private String regIp;
    private LocalDateTime rDate;
}
