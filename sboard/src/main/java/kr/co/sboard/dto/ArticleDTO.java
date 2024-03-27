package kr.co.sboard.dto;

import kr.co.sboard.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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
    private int heart;
    private String writer;
    private String regIp;
    private LocalDateTime rDate;

    private List<MultipartFile> files;
    private List<FileDTO> fileList;

    private String nick;
}
