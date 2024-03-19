package kr.co.sboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ConfigDTO {
    private String cate;
    private String boardName;
    private String admin;
    private int total;
    private LocalDateTime createDate;
}
