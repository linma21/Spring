package kr.co.ch09.entity;

import jakarta.persistence.*;
import kr.co.ch09.dto.User5DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user5")
public class User5 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    private String name;
    private String gender;
    private int age;
    private String addr;

    public User5DTO toDTO(){
        return  User5DTO.builder()
                .seq(seq)
                .name(name)
                .gender(gender)
                .age(age)
                .addr(addr)
                .build();
    }
}
