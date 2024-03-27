package kr.co.sboard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HeartDTO {
    private int hno;
    private String uid;
    private int no;
}
