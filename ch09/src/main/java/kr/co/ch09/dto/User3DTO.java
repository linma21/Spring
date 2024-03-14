package kr.co.ch09.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kr.co.ch09.entity.User3;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User3DTO {

    @NotBlank
    private String uid;
    @NotEmpty
    private String name;
    private String birth;
    private String hp;
    private String addr;

    public User3 toEntity(){
        return User3.builder()
                .uid(uid)
                .name(name)
                .birth(birth)
                .hp(hp)
                .addr(addr)
                .build();
    }
}
