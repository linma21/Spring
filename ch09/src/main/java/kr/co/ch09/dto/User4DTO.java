package kr.co.ch09.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kr.co.ch09.entity.User4;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User4DTO {
    @NotBlank
    private String uid;
    @NotEmpty
    private String name;
    private String gender;

    @Min(1)
    @Max(100)
    private Integer age;
    private String hp;
    private String addr;

    public User4 toEntity(){
        return User4.builder()
                .uid(uid)
                .name(name)
                .gender(gender)
                .age(age)
                .hp(hp)
                .addr(addr)
                .build();
    }
}
