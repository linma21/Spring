package kr.co.ch07.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
// 양방향 연관관계에서 toString()시 무한 순환 참조되는 상황을 막기위해 article은 toString 제외
@ToString(exclude = "article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;
    private String oName;
    private String sName;

    @OneToOne
    @JoinColumn(name = "ano")
    private Article article;

}
