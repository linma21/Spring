package kr.co.ch09.dto;

import jakarta.validation.constraints.*;
import kr.co.ch09.entity.User2;
import lombok.Builder;
import lombok.Data;

/*
    @NotBlank   : null, "", " " 모두 허용 안함
    @NotEmpty   : null, "" 허용 안함
    @NotNull    : null 허용 안함
 */
@Data
@Builder
public class User2DTO {
    /*
        - REST API 서비스 틍성상 프론트엔드에서 유효성 검사를 진행하기 어려움
        - @Validate, @NotBlank 등 백엔드에서 유효성 검사 실행
     */

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "영어 소문자와 숫자로 최소4자~최대10자 입력")
    private String uid;

    @NotEmpty
    private String name;

    @NotNull
    private String birth;

    @Email
    private String email;

    @Min(1)
    @Max(100)
    private int age;

    private String addr;

    public User2 toEntity(){
        return User2.builder()
                .uid(uid)
                .name(name)
                .birth(birth)
                .email(email)
                .age(age)
                .addr(addr)
                .build();
    }
}
