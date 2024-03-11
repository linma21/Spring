package kr.co.ch06.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 변수를 갖는 생성자
@NoArgsConstructor // 기본 생성자
@Builder
public class User1DTO {
    private String uid;
    private String name;
    private String birth;
    private String hp;
    private int age;
}
